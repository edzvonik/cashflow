package com.dzvonik.cashflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@SpringBootApplication
public class CashflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(CashflowApplication.class, args);
	}

//	@Bean
//	public ClassLoaderTemplateResolver secondaryTemplateResolver() {
//		ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
//		secondaryTemplateResolver.setPrefix("templates/");
//		secondaryTemplateResolver.setSuffix(".html");
//		secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
//		secondaryTemplateResolver.setCharacterEncoding("UTF-8");
//		secondaryTemplateResolver.setOrder(1);
//		secondaryTemplateResolver.setCheckExistence(true);
//
//		return secondaryTemplateResolver;
//	}

}
