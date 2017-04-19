package com.veio007.example.gx;

import com.gexin.clients.jedis.ScanParams;
import com.gexin.clients.jedis.ScanResult;
import com.gexin.jedis.IRedis;
import com.gexin.jedis.impl.JetisProxyFactory;

import java.util.Map;

public class redis {
	public static void main(String[] args) {
		System.setProperty("zkHost", "192.168.11.2");

		String setName = "bi-stat";
		IRedis jedis = JetisProxyFactory.getJedis(setName);
		query(jedis);

		System.exit(0);
	}

	static void keys(IRedis jedis) {
		Object o = jedis.eval("return redis.call('keys','*_APP_OMP_TOKEN_COUNT')", "tag");
		System.out.println(o);
	}

	static void query(IRedis jedis) {
		System.out.println(jedis.hgetAll("HW_APP_OMP_TOKEN_COUNT"));
		System.out.println(jedis.hgetAll("HW_APP_OMP_SEND_COUNT"));
		System.out.println(jedis.hgetAll("HW_APP_GTPS_SUCC_COUNT"));
		System.out.println(jedis.hgetAll("HW_APP_GTPS_FAIL_COUNT"));
	}

	static void clear(IRedis jedis) {
		jedis.del("HW_APP_OMP_TOKEN_COUNT");
		jedis.del("HW_APP_OMP_SEND_COUNT");
		jedis.del("HW_APP_GTPS_SUCC_COUNT");
		jedis.del("HW_APP_GTPS_FAIL_COUNT");

		jedis.del("XM_APP_OMP_TOKEN_COUNT");
		jedis.del("XM_APP_OMP_SEND_COUNT");
		jedis.del("XM_APP_GTPS_SUCC_COUNT");
		jedis.del("XM_APP_GTPS_FAIL_COUNT");

		jedis.del("MZ_APP_OMP_TOKEN_COUNT");
		jedis.del("MZ_APP_OMP_SEND_COUNT");
		jedis.del("MZ_APP_GTPS_SUCC_COUNT");
		jedis.del("MZ_APP_GTPS_FAIL_COUNT");
	}

	static void hscan(IRedis jedis) {
		ScanParams scanParams = new ScanParams();
		scanParams.count(100);
		String cursor = "0";
		ScanResult<Map.Entry<String, String>> result = jedis.hscan("HW_APP_OMP_TOKEN_COUNT", cursor, scanParams);
		// cursor为0表示数据已经取完
		System.out.println(result.getCursor() + "," + result.getResult());
	}
}