package com.veio007.example.spring;

import org.apache.commons.lang3.math.NumberUtils;
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.handler.ErrorHandler;
import org.eclipse.jetty.server.handler.gzip.GzipHandler;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.management.MBeanServer;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class JettySpringTest {
	public static void main(String[] args) throws Exception {
		startJetty();
	}

	public static void test() {
	}


	public static void startJetty() throws Exception {
		List<Integer> JETTY_SERVER_PORT = new ArrayList<>();
		JETTY_SERVER_PORT.add(8006);
		List<Integer> SSL_SERVER_PORT = new ArrayList<>();
		SSL_SERVER_PORT.add(8443);

		// jetty线程最大闲置时间
		int maxIdleTime = NumberUtils.toInt(System.getProperty("jetty.maxIdleTime"), 3000);
		// jetty 最小线程数
		int minThreads = NumberUtils.toInt(System.getProperty("jetty.minThreads"), 200);
		// jetty 最大线程数
		int maxThreads = NumberUtils.toInt(System.getProperty("jetty.maxThreads"), 200);
		// jetty 最大队列长度
		int maxQueued = NumberUtils.toInt(System.getProperty("jetty.maxQueued"), -1);
		// jetty connect socket 最大队列长度
		int acceptorBackLog = NumberUtils.toInt(System.getProperty("jetty.acceptorBackLog"), 50);
		// jetty connect socket acceptor线程数
		int acceptors = Runtime.getRuntime().availableProcessors() + 1;
		// jetty请求超时时间
		// int timeout =
		// NumberUtils.toInt(System.getProperty("jetty.maxTimeout"), 10000);
		acceptors = NumberUtils.toInt(System.getProperty("jetty.acceptors"), acceptors);
		maxQueued = maxQueued == -1 ? Integer.MAX_VALUE : maxQueued;

		// 创建线程池
		QueuedThreadPool threadPool = new JettyThreadPool(minThreads, maxThreads, maxIdleTime,
				new LinkedBlockingQueue<Runnable>(maxQueued), maxQueued);

		Server server = new Server(threadPool);

		List<Connector> connectors = new ArrayList<>();
		for (Integer port : JETTY_SERVER_PORT) {
			ServerConnector connector = new ServerConnector(server);
			connector.setAcceptQueueSize(acceptorBackLog);
			connector.setPort(port);
			connector.setIdleTimeout(maxIdleTime);
			connectors.add(connector);
		}

		for (Integer port : SSL_SERVER_PORT) {
			SslContextFactory sslContext = new SslContextFactory();
			sslContext.setKeyStorePath("config/keystore");
			sslContext.setKeyStorePassword("123456");
			sslContext.setKeyManagerPassword("123456");
			// 支持老版ssl协议
			sslContext.setIncludeProtocols("SSL", "SSLv2", "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2");
			sslContext.setExcludeProtocols("");

			HttpConfiguration httpsConfig = new HttpConfiguration();
			httpsConfig.setSecurePort(port);
			httpsConfig.setSecureScheme("https");
			httpsConfig.setOutputBufferSize(32768);
			httpsConfig.addCustomizer(new SecureRequestCustomizer());
			// 不响应版本号
			httpsConfig.setSendServerVersion(false);

			ServerConnector httpsConnector = new ServerConnector(server,
					new SslConnectionFactory(sslContext, "http/1.1"), new HttpConnectionFactory(httpsConfig));
			httpsConnector.setPort(port);
			httpsConnector.setIdleTimeout(maxIdleTime);

			connectors.add(httpsConnector);
		}


		Connector[] connArr = new Connector[connectors.size()];
		connectors.toArray(connArr);
		server.setConnectors(connArr);

		//
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/");
		webAppContext.setResourceBase("./webapp");
		webAppContext.setErrorHandler(new ErrorHandler());

		// gzip压缩
		GzipHandler gzipHandle = new GzipHandler();
		gzipHandle.setIncludedMethods("POST", "GET");
		gzipHandle.setIncludedMimeTypes("text/html", "application/json");
		gzipHandle.setMinGzipSize(1000);
		server.setHandler(gzipHandle);
		// 错误响应内容
		gzipHandle.setHandler(webAppContext);

		// jmx
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		MBeanContainer mBeanContainer = new MBeanContainer(mBeanServer);
		server.addEventListener(mBeanContainer);
		server.addBean(mBeanContainer);

		server.setStopAtShutdown(true);
		try {
			server.start();

			//test
			test();
			server.join();
		} catch (Exception e) {
		}

	}
}
