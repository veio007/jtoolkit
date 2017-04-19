package com.veio007.example.example;

//tag 字符串处理

public class StringTest {
	public static void main(String[] args) {
		String s = "323_dadfd_3234";
		System.out.println(s.replaceFirst("[0-9]+\\_", ""));
	}
}
