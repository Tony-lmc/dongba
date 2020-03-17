package com.jt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jt.interceptor.UserInterceptor;

@Configuration
public class MvcConfigurer implements WebMvcConfigurer{
	@Autowired
	private UserInterceptor userInterceptor;

	//开启匹配后缀型配置
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		configurer.setUseSuffixPatternMatch(true);
	}
	
	@Override
	public
	void addInterceptors(InterceptorRegistry registry) {
		String interceptorCart = "/cart/**";//购物车拦截
		String interceptorOrder = "/order/**";//订单拦截
		
		registry.addInterceptor(userInterceptor).addPathPatterns(interceptorCart,interceptorOrder);
	}
}
