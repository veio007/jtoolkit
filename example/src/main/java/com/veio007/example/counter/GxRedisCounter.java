package com.veio007.example.counter;

import com.gexin.jedis.impl.RedisAtomicCounter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 第三方消息发送计数实现 存储在counter
 * com.veio007.example.example:
 * GxRedisCounter com.veio007.com.veio007.example.example.config.counter = new GxRedisCounter(setname, 1000, 60000);
 * com.veio007.com.veio007.example.example.config.counter.increment("type", "appid");
 */
public class GxRedisCounter {

	/**
	 * 计数+1操作
	 *
	 * @param bigKey
	 * @param smallKey
	 */
	public void increment(String bigKey, String smallKey) {
		try {
			RedisAtomicCounter counter = counterMap.get(bigKey);
			if (counter == null) {
				synchronized (GxRedisCounter.class) {
					counter = counterMap.get(bigKey);
					if (counter == null) {
						counter = newCounter(bigKey);
						counterMap.put(bigKey, counter);
					}
				}
			}

			counter.increment(smallKey);
		} catch (Throwable ex) {
//			LogManager.exception(ex, "ThirdPartyMsgCounter increment err", "bigKey", bigKey, "smallKey", smallKey);
		}
	}

	private RedisAtomicCounter newCounter(String key) {
		return RedisAtomicCounter.getInstance(setName,
				key,
				-1,
				commitInterval,
				commitTime);
	}
	private final Map<String, RedisAtomicCounter> counterMap = new ConcurrentHashMap<String, RedisAtomicCounter>();
	private final String setName;
	private final int commitInterval;
	private final int commitTime;

	public GxRedisCounter(String setName, int commitInterval, int commitTime) {
		this.setName = setName;
		this.commitInterval = commitInterval;
		this.commitTime = commitTime;
	}
}
