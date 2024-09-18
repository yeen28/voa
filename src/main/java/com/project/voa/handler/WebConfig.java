package com.project.voa.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	private final UserInfoArgumentResolver userInfoArgumentResolver;

	@Autowired
	public WebConfig(UserInfoArgumentResolver userInfoArgumentResolver) {
		this.userInfoArgumentResolver = userInfoArgumentResolver;
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(userInfoArgumentResolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
				.addResourceLocations("classpath:/META-INF/resources/",
						"classpath:/resources/",
						"classpath:/static/",
						"classpath:/public/",
						"classpath:/dist/");
	}
}
