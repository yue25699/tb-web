package com.tb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tb.mapper") //为mapper接口创建代理对象
public class SpringBootRun {
	
	
	public static void main(String[] args) {
		
		SpringApplication.run(SpringBootRun.class, args);
	}

}