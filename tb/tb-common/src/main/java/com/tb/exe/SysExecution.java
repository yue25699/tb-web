package com.tb.exe;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tb.vo.SysResult;

import lombok.extern.slf4j.Slf4j;
//返回数据为JSON
@RestControllerAdvice  //异常通知 对Controller层生效
@Slf4j				   //记录日志
public class SysExecution {
	
	//当系统中出现运行时异常时生效
	@ExceptionHandler(RuntimeException.class)
	public SysResult error(Exception exception) {
		exception.printStackTrace();
		log.error(exception.getMessage());
		return SysResult.fail();
	}
}
