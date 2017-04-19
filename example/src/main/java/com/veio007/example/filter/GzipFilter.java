package com.veio007.example.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class GzipFilter implements Filter {
	private Set<String> mimeTypes = new HashSet<String>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String s = filterConfig.getInitParameter("mimeTypes");
		if (s != null) {
			StringTokenizer tok = new StringTokenizer(s, ",", false);
			while (tok.hasMoreTokens()) {
				mimeTypes.add(tok.nextToken());
			}
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) rsp;
		String contentEncoding = request.getHeader("content-encoding");
		if ((contentEncoding != null) && (contentEncoding.toLowerCase().indexOf("gzip") > -1)) {
			if (!(request instanceof GZIPRequestWrapper)) {
				request = new GZIPRequestWrapper(request);
			}
		}

		chain.doFilter(request, response);
	}

}