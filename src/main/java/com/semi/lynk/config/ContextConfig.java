package com.semi.lynk.config;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.semi.lynk")

@MapperScan(basePackages = "com.semi.lynk", annotationClass = Mapper.class)
public class ContextConfig {
}