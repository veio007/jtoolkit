package com.veio.example;

// 反射封装

import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Set;

public class reflectionTest {
	public static void main(String[] args) {
		Reflections reflections = new Reflections("com.veio.example");
		Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Resource.class);
		System.out.println(annotatedClasses);
	}
}


@Resource
class Bean {
	@PostConstruct
	public void is() {

	}
}