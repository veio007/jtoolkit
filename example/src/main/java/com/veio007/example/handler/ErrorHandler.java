package com.veio007.example.handler;

import com.gexin.fastjson.JSONObject;
import com.gexin.fastjson.annotation.JSONField;
import com.veio.util.StackTraceUtil;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpMethod;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.ByteArrayISO8859Writer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ErrorHandler extends org.eclipse.jetty.server.handler.ErrorHandler {

    private static final Logger LOG = Log.getLogger(ErrorHandler.class);

    public ErrorHandler() {
        this.setShowStacks(false);
    }

    @Override
    public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String method = request.getMethod();
        if (!HttpMethod.GET.is(method) && !HttpMethod.POST.is(method) && !HttpMethod.HEAD.is(method)) {
            baseRequest.setHandled(true);
            return;
        }

        if (this instanceof ErrorPageMapper) {
            String error_page = ((ErrorPageMapper) this).getErrorPage(request);
            if (error_page != null && request.getServletContext() != null) {
                String old_error_page = (String) request.getAttribute(ERROR_PAGE);
                if (old_error_page == null || !old_error_page.equals(error_page)) {
                    request.setAttribute(ERROR_PAGE, error_page);

                    Dispatcher dispatcher = (Dispatcher) request.getServletContext().getRequestDispatcher(error_page);
                    try {
                        if (dispatcher != null) {
                            dispatcher.error(request, response);
                            return;
                        }
                        LOG.warn("No error page " + error_page);
                    } catch (ServletException e) {
                        LOG.warn(Log.EXCEPTION, e);
                        return;
                    }
                }
            }
        }

        baseRequest.setHandled(true);
        response.setContentType(MimeTypes.Type.APPLICATION_JSON_UTF_8.asString());
        if (getCacheControl() != null)
            response.setHeader(HttpHeader.CACHE_CONTROL.asString(), getCacheControl());
        ByteArrayISO8859Writer writer = new ByteArrayISO8859Writer(4096);
        String reason = (response instanceof Response) ? ((Response) response).getReason() : null;
        handleErrorPage(request, writer, response.getStatus(), reason);
        writer.flush();
        response.setContentLength(writer.size());
        writer.writeTo(response.getOutputStream());
        writer.destroy();
    }

    @Override
    protected void handleErrorPage(HttpServletRequest request, Writer writer, int code, String message) throws IOException {
        if (message == null)
            message= HttpStatus.getMessage(code);
        Error error =new Error();
        error.setStatus(code);
        error.setMessage(message);
        if (isShowStacks()){
            Throwable th = (Throwable)request.getAttribute("javax.servlet.error.exception");
            error.setDetail(StackTraceUtil.getStackTrace(th));
        }
        writer.write(JSONObject.toJSONString(error));
    }


    public static class Error {
        @JSONField(ordinal = 1)
        private int status;

        @JSONField(ordinal = 2)
        private String message;

        @JSONField(ordinal = 3)
        private String detail;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }
    }
}