package com.veio.spring;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

import java.beans.PropertyDescriptor;

public class MyInstantiationAwaveBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

	// 实例化Bean前调用
	public Object postProcessBeforeInstantiation(Class beanClass, String beanName) {
		System.out.println(beanName);
		System.out.println(beanClass.getName());
		return null;
	}

	// 实例化Bean后调用
	public boolean postProcessAfterInstantiation(Object bean, String beanName) {
		System.out.println(beanName);
		System.out.println(bean);
		return true;
	}

	// 设置属性时调用
	public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) {
		System.out.println(pvs);
		System.out.println(pds);
		System.out.println(bean);
		System.out.println(beanName);
		return pvs;
	}
}
