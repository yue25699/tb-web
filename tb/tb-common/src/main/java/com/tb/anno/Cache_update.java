package com.tb.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  //标识运行时有效
@Target({ElementType.METHOD})//注解的适用范围
public @interface Cache_update {
	String key() default "";//接受用户key
}
