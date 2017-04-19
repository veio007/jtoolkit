package com.veio007.example.example;

//tag 动态代理

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Work {
	void doing();
}

class LiWei implements Work {

	@Override
	public void doing() {
		System.out.println("liwei doing work");
	}
}

class ProxyHandler implements InvocationHandler {
	public Work people;

	public ProxyHandler(Work w) {
		this.people = w;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before do work");
		Object obj = method.invoke(people, args);
		System.out.println("after do work");
		return obj;
	}
}

public class ProxyTest {
	public static void main(String[] args) {
		Work p = new LiWei();
		ProxyHandler handler = new ProxyHandler(p);
		Work proxy = (Work) Proxy.newProxyInstance(p.getClass().getClassLoader(), p.getClass().getInterfaces(), handler);

		proxy.doing();
	}
}
