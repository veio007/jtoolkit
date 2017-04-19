package com.veio007.example.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

public class GZIPRequestStream extends ServletInputStream {
	private GZIPInputStream in;

	public GZIPRequestStream(HttpServletRequest request) throws IOException {
		this.in = new GZIPInputStream(request.getInputStream());
	}

	@Override
	public int read() throws IOException {
		return in.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return in.read(b);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return in.read(b, off, len);
	}

	@Override
	public void close() throws IOException {
		in.close();
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	public boolean isReady() {
		return false;
	}

	@Override
	public void setReadListener(ReadListener readListener) {

	}
}