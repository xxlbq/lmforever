package com.lubq.lm.spr;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class Main {

	public static void main(String[] args) throws Exception {
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(
//				"com/lubq/lm/spr/spring.xml");
		BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(
        "appcontext.xml"));
		Hello h = (Hello) beanFactory.getBean("hello");
		h.sayHello("fhway");
	}
}
