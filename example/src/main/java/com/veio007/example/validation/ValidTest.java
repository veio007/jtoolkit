package com.veio007.example.validation;


import com.alibaba.fastjson.JSON;

public class ValidTest {
	public static void main(String[] args) {
		String json = "{\"Contentx\":\"contentxx\", \"offline\":100, \"tpl\":{\"title\":\"titleee\", \"text\":\"texttt\", \"action\":{\"name\":\"nameee\"}}, \"tt\":\"t\", \"list\":[\"1\",\"2\",3]}";
		Message msg = JSON.parseObject(json, Message.class);
		System.out.println(msg);
	}
}
