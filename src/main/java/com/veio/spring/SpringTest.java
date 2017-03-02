package com.veio.spring;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SpringTest {
	public static void main(String[] args) throws IOException {
		loads();
	}

	static void load() throws IOException {
		// 使用spring的资源访问工具获取配置文件内容
		Resource res = new ClassPathResource("spring.xml");
		EncodedResource encRes = new EncodedResource(res,"UTF-8");
		System.out.println(FileCopyUtils.copyToString(encRes.getReader()));
	}

	static void loads() throws IOException {
		// 按匹配规则加载满足条件的文件资源
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resArr = resolver.getResources("classpath*:**/spring.xml");
		for (Resource resource : resArr) {
			System.out.println(resource.getURI());
		}
	}
}
