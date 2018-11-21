package com.jshx;


import javax.servlet.*;

import com.jshx.filter.MyFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*import org.springframework.boot.context.embedded.FilterRegistrationBean;*/
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//重点
@ServletComponentScan
public class WlwMonitorApplication {

	public static void main(String[] args) {

		SpringApplication.run(WlwMonitorApplication.class, args);
	}

	/**
	 * 配置过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean someFilterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(sessionFilter());
		registration.addUrlPatterns("/*");
		registration.addInitParameter("paramName", "paramValue");
		registration.setName("myFilter");
		return registration;
	}

	/**
	 * 创建一个bean
	 * @return
	 */
	@Bean(name = "myFilter")
	public Filter sessionFilter() {
		return new MyFilter();
	}

}
