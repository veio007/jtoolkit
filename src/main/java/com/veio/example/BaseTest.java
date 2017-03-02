package com.veio.example;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class BaseTest {
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<>();
		map.put("1", "1");
		map.put("2", "2");

		Iterator<String> iter = map.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			map.remove(key);
		}

		System.out.println(map);
	}
}
