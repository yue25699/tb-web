package com.tb.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tb.anno.Cache_update;

import redis.clients.jedis.Jedis;

@Component    //将该类交给spring容器管理
@Aspect       //表示这是一个切面类
public class RedisUpdateAspect {
	
	@Autowired(required = false)
	private Jedis jedis;
	
	@Around("@annotation(cache_update)")
	public void delRedis(ProceedingJoinPoint pdjp,Cache_update cache_update) {
		String key = cache_update.key();
		try {
			if(String.valueOf(pdjp.getArgs()[0]) != null) {
				String valueOf = String.valueOf(pdjp.getArgs()[0]);
				key =key+"_"+valueOf;
				jedis.del(key);
				pdjp.proceed();
			}else {
				pdjp.proceed();
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

}
