package com.tb.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tb.enu.KEY_ENUM;

@Retention(RetentionPolicy.RUNTIME)  //标识运行时有效
@Target({ElementType.METHOD})//注解的适用范围
public @interface Cache_Find {
	String key() default "";//接受用户key
	KEY_ENUM keyType() default KEY_ENUM.AOTU; //aotu 用户自动生成key   
	int secondes() default 0;  //0秒 永不失效
}
