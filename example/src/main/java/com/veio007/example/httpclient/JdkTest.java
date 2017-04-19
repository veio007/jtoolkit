package com.veio007.example.httpclient;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class JdkTest {
	public static void main(String[] args) {
		httpGET();
	}

	private static void httpGET() {
		StringBuffer responseMessage = new StringBuffer();
		HttpURLConnection connection = null;
		java.net.URL reqUrl = null;
		OutputStreamWriter reqOut = null;
		InputStream in = null;
		BufferedReader br = null;
		String param = "";
		String url = "https://www.baidu.com";
		int time = 3;

		while (time-- > 0) {
			try {
				reqUrl = new java.net.URL(url);
				connection = (HttpURLConnection) reqUrl.openConnection();
				connection.setUseCaches(false);
				connection.setDoOutput(true);
				connection.setDoInput(true);
				connection.setRequestMethod("GET");
				connection.setConnectTimeout(1000 * 3);
				connection.setReadTimeout(1000 * 5);

				int charCount = -1;
				in = connection.getInputStream();
				br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				while ((charCount = br.read()) != -1) {
					responseMessage.append((char) charCount);
				}

				System.out.println(responseMessage);
			} catch (Exception ex) {
			} finally {
				try {
					in.close();
					reqOut.close();
				} catch (Exception e) {
				}
			}
		}
	}
}