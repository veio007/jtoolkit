package com.veio007.example.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZIPResponseStream extends ServletOutputStream {
	private HttpServletResponse response;
	private GZIPOutputStream out;

	public GZIPResponseStream(HttpServletResponse response) throws IOException {
		this.response = response;
		this.out = new GZIPOutputStream(response.getOutputStream());
		response.addHeader("Content-Encoding", "gzip");
	}

	public void resetBuffer() {
		if (out != null && !response.isCommitted()) {
			response.setHeader("Content-Encoding", null);
		}
		out = null;
	}

	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		out.write(b, off, len);
	}

	@Override
	public void close() throws IOException {
		finish();
		out.close();
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	public void finish() throws IOException {
		out.finish();
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setWriteListener(WriteListener writeListener) {

	}
}