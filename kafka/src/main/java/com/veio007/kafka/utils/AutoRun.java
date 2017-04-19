package com.veio007.kafka.utils;

import com.veio007.kafka.bean.StatusBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softee.management.helper.MBeanRegistration;

import javax.management.ObjectName;
import java.util.concurrent.atomic.AtomicBoolean;

public class AutoRun {
	public static synchronized void run() {
		try {
			if (!loaded.compareAndSet(false, true)) {
				return;
			}
			StatusBean bean = new StatusBean();
			new MBeanRegistration(bean, new ObjectName("com.veio007.kafka.monitor:name=status")).register();
		} catch (Throwable ex) {
			logger.error("autorun error", ex);
		}
	}

	private static final Logger logger = LoggerFactory.getLogger(AutoRun.class);
	private static final AtomicBoolean loaded = new AtomicBoolean();
}
