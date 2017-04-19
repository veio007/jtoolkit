package com.veio007.kafka.core;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.atomic.AtomicLong;

public class AbstractBase {
	protected static final AtomicLong instanceSeq = new AtomicLong();

	protected String getClientId() {
		String clientId = System.getProperty("myName");
		if (StringUtils.isBlank(clientId)) {
			clientId = this.getClass().getSimpleName() + "-" + instanceSeq;
		}
		return clientId;
	}
}
