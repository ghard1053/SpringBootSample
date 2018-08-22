package com.example;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


@Configuration
public class MyBootAppConfig {

	@Bean
	MyDataBean myDataBean() {
		return new MyDataBean();
	}
	
	@Bean
	public ClassLoaderTemplateResolver templateResolver() {
		ClassLoaderTemplateResolver temlateResolver = new ClassLoaderTemplateResolver();
		temlateResolver.setPrefix("templates/");
		temlateResolver.setCacheable(false);
		temlateResolver.setSuffix(".html");
		temlateResolver.setTemplateMode("HTML5");
		temlateResolver.setCharacterEncoding("UTF-8");
		return temlateResolver;
	}
	
	@Bean
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.addDialect(new MyDialect());
		return templateEngine;
	}
}
