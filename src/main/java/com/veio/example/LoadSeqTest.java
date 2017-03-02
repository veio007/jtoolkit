package com.veio.example;

// tag 加载顺序 初始化顺序 优先级

class A {
	private static String s = "A静态变量";
	private String v = "A实例变量";
	static {
		System.out.println(s);
		System.out.println("A静态块");
	}

	public A() {
		System.out.println(v);
		System.out.println("A构造方法");
	}
}

class B extends A {
	private static String s = "B静态变量";
	private String v = "B实例变量";
	static {
		System.out.println(s);
		System.out.println("B静态块");
	}
	public B() {
		System.out.println(v);
		System.out.println("B构造方法");
	}
}


public class LoadSeqTest {
	public static void main(String[] args) {
		test.test(); // => false => static变量是按顺序加载的 代码加载顺序对代码很重要
		/*
		 * 静态变量和静态块按顺序加载，父类先于子类加载，成员变量先于构造方法执行
		 */
		B b = new B();
	}

	private static LoadSeqTest test = new LoadSeqTest();
	private static boolean b = false;
	public LoadSeqTest() {
		b = true;
	}

	public void test() {
		System.out.println(b);
	}
}
