package com.tci.rest.webservices.restfullwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class RestFullWebservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestFullWebservicesApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver(){
	//	SessionLocaleResolver localeResolve = new SessionLocaleResolver();
		AcceptHeaderLocaleResolver localeResolve = new AcceptHeaderLocaleResolver();
		localeResolve.setDefaultLocale(Locale.US);
		return localeResolve;
	}
/*
	@Bean
	public ResourceBundleMessageSource bundleMessageSource(){
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}*/
}


