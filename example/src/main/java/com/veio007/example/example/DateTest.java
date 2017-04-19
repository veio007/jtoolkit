package com.veio007.example.example;

// tag 日期 时间 date time 转换

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;

public class DateTest {
	public static void main(String[] args) throws ParseException {
		//  字符串转日期或者时间戳
		System.out.println(DateUtils.parseDate("201602201201", "yyyyMMddHHmm").getTime());
	}
}
