package com.shanghai.wifishare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wifishared.common.framework.jwt.HTTPBearerAuthorizationFilter;
import com.wifishared.common.framework.redis.RedisManager;
import com.wifishared.common.framework.redis.RedisStringManager;

@Configuration
public class TokenConfig {

	@Autowired
	private RedisStringManager redisManager;
	
	@Bean  
    public FilterRegistrationBean tokenAuthenticationFilter() {  
        FilterRegistrationBean registration = new FilterRegistrationBean();  
        registration.setFilter(new HTTPBearerAuthorizationFilter(redisManager));
        registration.addUrlPatterns("/auth/*");  
        registration.setName("tokenAuthenticationFilter");  
        return registration;  
    }  
	
}
