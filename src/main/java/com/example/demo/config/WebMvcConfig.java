package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String filePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*
        * 图片上传出现的问题
        * addResourceHandler("/file/**")：访问映射的路径。
        * addResourceLocations(“file:D:/manager/”)：资源的绝对路径。
        * */

        registry.addResourceHandler("/data/**").addResourceLocations("file:" + filePath);
    }
}
