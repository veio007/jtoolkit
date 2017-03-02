package com.veio.example;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.List;

// tag 类型转换

public class ConvertTest {
	public static void main(String[] args) {
		// list转数组
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("b");
		String[] arr = new String[list.size()];
		list.toArray(arr);

		// List<int>转换为数组
		List<Integer> intList = new ArrayList<>();
		intList.add(1);
		intList.add(2);
		int[] intarr = Ints.toArray(intList);


	}
}
