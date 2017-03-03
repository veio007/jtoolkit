package com.veio.spring;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

public class Bean implements BeanFactoryAware, BeanNameAware, InitializingBean,DisposableBean {
	private String str;

	public void setStr(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return "Bean{" +
				"str='" + str + '\'' +
				'}';
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("factory");
	}

	@Override
	public void setBeanName(String name) {
		System.out.println(name);
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("destory");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("properset");
	}

	public void myInit() {
		System.out.println("myinit");
	}

	public void myDestory() {
		System.out.println("mydestory");
	}
}
