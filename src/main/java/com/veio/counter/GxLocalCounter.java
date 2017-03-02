package com.veio.counter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * 本地计数器实现
 * example:
 * GxLocalCounter.getInstance().increment("recordtype", "appid");
 */
public class GxLocalCounter {

	/**
	 * 对指定计数器做增加计数操作
	 *
	 * @param key     大key
	 * @param skey    小key
	 * @param incrNum 增加数量
	 */
	public void increment(String key, String skey, int incrNum) {
		Map<String, AtomicLong> map = counterMap.get(key);
		if (map == null) {
			synchronized (GxLocalCounter.class) {
				map = counterMap.get(key);
				if (map == null) {
					map = new ConcurrentHashMap<String, AtomicLong>();
					counterMap.put(key, map);
				}
			}
		}

		AtomicLong counter = map.get(skey);
		if (counter == null) {
			synchronized (GxLocalCounter.class) {
				counter = map.get(skey);
				if (counter == null) {
					counter = new AtomicLong();
					map.put(skey, counter);
				}
			}
		}

		counter.addAndGet(incrNum);
	}

	/**
	 * 对指定计数器做计数加1操作
	 *
	 * @param key  大key
	 * @param skey 小key
	 */
	public void increment(String key, String skey) {
		increment(key, skey, 1);
	}

	/**
	 * 获取指定计数器的值
	 *
	 * @param key  大key
	 * @param skey 小key
	 * @return
	 */
	public long get(String key, String skey) {
		Map<String, AtomicLong> map = counterMap.get(key);
		if (map == null) {
			return 0;
		}
		AtomicLong counter = map.get(skey);
		if (counter == null) {
			return 0;
		}

		return counter.get();
	}

	/**
	 * 获取指定计数器的值并且清除计数器
	 *
	 * @param key
	 * @param skey
	 * @return
	 */
	public long getAndReset(String key, String skey) {
		long c = get(key, skey);
		reset(key, skey);
		return c;
	}

	private void reset(String key, String skey) {
		synchronized (GxLocalCounter.class) {
			Map<String, AtomicLong> map = counterMap.get(key);
			if (map == null) {
				return;
			}
			map.remove(skey);
		}
	}

	/**
	 * 返回大key对应的map集合
	 *
	 * @param key 大key
	 * @return
	 */
	public Map<String, AtomicLong> getCounters(String key) {
		return counterMap.get(key) == null ? emptyMap : counterMap.get(key);
	}

	public static GxLocalCounter getInstance() {
		return counter;
	}

	// map<计数类型,map<计数维度,计数器>> 和redis的map类似
	private static final Map<String, Map<String, AtomicLong>> counterMap = new ConcurrentHashMap<String, Map<String, AtomicLong>>();

	private GxLocalCounter() {
	}

	private static final Map<String, AtomicLong> emptyMap = new ConcurrentHashMap<String, AtomicLong>();

	private static final GxLocalCounter counter = new GxLocalCounter();
}
