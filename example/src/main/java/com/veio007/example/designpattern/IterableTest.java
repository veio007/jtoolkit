package com.veio007.example.designpattern;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// tag 迭代器实现 Iterator Iterable

class Test {
	private static List<String> set = new ArrayList<String>();
	static {
		set.add("1");
		set.add("2");
		set.add("3");
	}

	public static Iterable make() {
		return new TestIterable();
	}
	public static class TestIterable implements Iterable {

		@Override
		public Iterator iterator() {
			return new TestIterator();
		}

		public static class TestIterator implements Iterator {

			@Override
			public boolean hasNext() {
				return cur < set.size();
			}

			@Override
			public Object next() {
				return set.get(cur++);
			}

			@Override
			public void remove() {

			}

			private int cur = 0;
		}
	}
}

public class IterableTest {
	public static void main(String[] args) {
		for( Object s: Test.make()) {
			System.out.println(s);
		}
	}
}
