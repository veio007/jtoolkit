package com.veio.spring;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

public class SpringTest {
	public static void main(String[] args) throws IOException {
		applicationContext();
	}

	static void load() throws IOException {
		// 使用spring的资源访问工具获取配置文件内容
		Resource res = new ClassPathResource("spring.xml");
		EncodedResource encRes = new EncodedResource(res,"UTF-8");
		System.out.println(FileCopyUtils.copyToString(encRes.getReader()));
	}

	static void loads() throws IOException {
		// 按匹配规则加载满足条件的文件资源 支持ant风格表达式
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		Resource[] resArr = resolver.getResources("classpath*:**/spring.xml");
		for (Resource resource : resArr) {
			System.out.println(resource.getURI());
		}
	}

	// bean需要等到getBean方法调用的时候才会初始化并缓存 在3.1开始不被推荐使用
	static void beanFactory() {
		Resource res = new ClassPathResource("spring.xml");
		BeanFactory beanFactory = new XmlBeanFactory(res);
		String s = (String)beanFactory.getBean("str");
		System.out.println(s);
	}

	// 和beanFactory类似，在3.1开始推荐使用
	static void beanFactory1() {
		Resource res = new ClassPathResource("spring.xml");
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		XmlBeanDefinitionReader  reader = new XmlBeanDefinitionReader(factory);
		reader.loadBeanDefinitions(res);
		String s = (String)factory.getBean("str");
		System.out.println(s);
	}

	static void applicationContext() {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"spring.xml"});
		// bean生命周期查看
		System.out.println(context.getBean("myBean"));
	}

	// 使用注解配置的类替代xml文件初始化容器
	static void annotationApplicationContext() {
		ApplicationContext context = new AnnotationConfigApplicationContext(Beans.class);
		System.out.println(context.getBean("str"));
		System.out.println(context.getBean("str"));
		System.out.println(context.getBean("str"));
	}
}


@Configuration
class Beans {
	@Bean(name="str")
	@Scope(scopeName = "prototype")
	public String getStr() {
		return "aaa"+seq.getAndIncrement();
	}

	private static final AtomicLong seq = new AtomicLong();
}