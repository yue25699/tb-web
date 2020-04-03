package com.tb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test1")
public class Test01 {
	
	@RequestMapping("/abc/{param}/{web}")
	public String abc(@PathVariable String param ,
					  @PathVariable String web) {
		System.out.println(param+"  "+web);
		System.out.println("进入web方法执行");
		return "目标方法执行正确";
	}
}
