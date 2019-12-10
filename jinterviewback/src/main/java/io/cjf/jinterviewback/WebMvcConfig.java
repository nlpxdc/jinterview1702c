package io.cjf.jinterviewback;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//将url 中的image 映射到 d:/img/
		registry.addResourceHandler("/image/**").addResourceLocations("file:d:/img/");
	}
	
	

	
}
