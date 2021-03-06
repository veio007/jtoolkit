package com.veio007.example.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;

public class GZIPResponseWrapper extends HttpServletResponseWrapper {
	private HttpServletResponse response;
	private GZIPResponseStream os;
	private PrintWriter writer;
	private boolean compress = true;

	public GZIPResponseWrapper(HttpServletResponse response) {
		super(response);
		this.response = response;
	}

	@Override
	public void setStatus(int status) {
		if (status < 200 || status >= 300) {
			compress = false;
		}
	}

	@Override
	public void addHeader(String name, String value) {
		if (!"content-length".equalsIgnoreCase(name)) {
			super.addHeader(name, value);
		}
	}

	@Override
	public void setContentLength(int length) {
		// do nothing
	}

	@Override
	public void setIntHeader(String name, int value) {
		if (!"content-length".equalsIgnoreCase(name)) {
			super.setIntHeader(name, value);
		}
	}

	@Override
	public void setHeader(String name, String value) {
		if (!"content-length".equalsIgnoreCase(name)) {
			super.setHeader(name, value);
		}
	}

	@Override
	public void flushBuffer() throws IOException {
		if (writer != null) {
			writer.flush();
		}
		if (os != null) {
			os.finish();
		} else {
			getResponse().flushBuffer();
		}
	}

	@Override
	public void reset() {
		super.reset();
		if (os != null) {
			os.resetBuffer();
		}
		writer = null;
		os = null;
		compress = true;
	}

	@Override
	public void resetBuffer() {
		super.resetBuffer();
		if (os != null) {
			os.resetBuffer();
		}
		writer = null;
		os = null;
	}

	@Override
	public void sendError(int status, String msg) throws IOException {
		resetBuffer();
		super.sendError(status, msg);
	}

	@Override
	public void sendError(int status) throws IOException {
		resetBuffer();
		super.sendError(status);
	}

	@Override
	public void sendRedirect(String location) throws IOException {
		resetBuffer();
		super.sendRedirect(location);
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (!response.isCommitted() && compress) {
			if (os == null) {
				os = new GZIPResponseStream(response);
			}
			return os;
		} else {
			return response.getOutputStream();
		}
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (writer == null) {
			writer = new PrintWriter(getOutputStream());
		}
		return writer;
	}
}