package com.ai.chat.config;


import com.ai.chat.Interceptor.AdminInterceptor;
import com.ai.chat.Interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author aoufgo
 * @date 2021/4/3 下午4:37
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Resource
    AdminInterceptor loginInterceptor;
    @Resource
    TokenInterceptor tokenInterceptor;
    /**资源处理器:设置静态资源的url*/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("/WEB-INF/"+"/image/");
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/"+"/static/");
        //虚拟路径
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:/Volumes/mac-data/chatAvatar/");
        registry.addResourceHandler("/faceImgs/**").addResourceLocations("file:/Volumes/mac-data/faceImgs/");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/admin_page/**").excludePathPatterns("/admin_page/adminLogin").excludePathPatterns("/static/**");
        registry.addInterceptor(tokenInterceptor).addPathPatterns("/user/**")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/user/faceRegister")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/codeLogin")
                .excludePathPatterns("/user/faceLogin")
                .excludePathPatterns("/user/sendCode")
                .excludePathPatterns("/user/checkName")
                .excludePathPatterns("/user/changePW")
                .excludePathPatterns("/user/quit");
    }
}
