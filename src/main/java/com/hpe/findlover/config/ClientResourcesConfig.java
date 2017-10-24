package com.hpe.findlover.config;

import com.hpe.findlover.interceptor.UserHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * 解析器配置类
 */
@Configuration
public class ClientResourcesConfig extends WebMvcConfigurerAdapter {

    /**
     * 添加自定义参数解析器
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new UserHandlerMethodArgumentResolver());
    }

}