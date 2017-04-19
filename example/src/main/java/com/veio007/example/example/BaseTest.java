package com.veio007.example.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;

public class BaseTest {
	static Logger logger = LoggerFactory.getLogger("aa");
	static ThreadLocal<ArrayList<Map.Entry<String, Long>>> trace = new ThreadLocal<ArrayList<Map.Entry<String, Long>>>(){
		@Override
		protected ArrayList<Map.Entry<String, Long>> initialValue() {
			return new ArrayList<>();
		}
	};
	public static void main(String[] args) throws IOException {
		System.out.println(InetAddress.getByName("www.baidu.com").getHostAddress());
	}


	public static class Element {
		private long remoteTime;
		private long localTime;
		private long reqTime;

		public long getRemoteTime() {
			return remoteTime;
		}

		public void setRemoteTime(long remoteTime) {
			this.remoteTime = remoteTime;
		}

		public long getLocalTime() {
			return localTime;
		}

		public void setLocalTime(long localTime) {
			this.localTime = localTime;
		}

		public long getReqTime() {
			return reqTime;
		}

		public void setReqTime(long reqTime) {
			this.reqTime = reqTime;
		}

		@Override
		public String toString() {
			return "Element{" +
					"remoteTime=" + remoteTime +
					", localTime=" + localTime +
					", reqTime=" + reqTime +
					'}';
		}
	}
}
