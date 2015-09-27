package com.sky.assignment;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

@Configuration
@EnableCaching
@ComponentScan("com.sky.assignment")
public class SpringConfig {

    @Bean
    public ServletRegistrationBean dispatcherServlet(ApplicationContext applicationContext) {
        DispatcherServlet servlet = new DispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        return new ServletRegistrationBean(servlet, "/*");
    }
    
    /**
     * Configure a guava cacheManager with an expiration time of 5 mins
     * @return
     */
    @Bean
    @SuppressWarnings("unchecked")
    public CacheManager cacheManager() {
    	SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		Cache cache =  new ConcurrentMapCache("recommendations",
	             CacheBuilder.newBuilder().expireAfterWrite(5, TimeUnit.MINUTES).maximumSize(100).build(new CacheLoader() {
	
						@Override
						public Object load(Object arg0) throws Exception {
							return null;
						}
					}).asMap(), false);
	     
	     simpleCacheManager.setCaches(Arrays.asList(cache));
	     return simpleCacheManager;
    }
    
}
