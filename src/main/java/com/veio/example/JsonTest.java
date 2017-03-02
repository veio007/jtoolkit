package com.veio.example;


import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

// tag json处理
public class JsonTest {
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		map.put("aa",10);
		System.out.println(JSONObject.toJSONString(map));
	}
}
