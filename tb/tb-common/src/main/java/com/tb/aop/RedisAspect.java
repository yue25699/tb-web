package com.tb.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.tb.anno.Cache_Find;
import com.tb.enu.KEY_ENUM;
import com.tb.util.ObjectMapperUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;

@Component   //将对象交给spring容器管理
@Aspect      // 标识他是一个切面
public class RedisAspect {
	//容器初始化时不需要实例该对象
	//只有用户使用时才初始化
	//一般工具类中添加该注解
	@Autowired(required = false)
	private ShardedJedis jedis;
	
	//使用该方法可以直接获取注解的对象
	@Around("@annotation(cache_find)")
	public Object around(ProceedingJoinPoint joinPoint,Cache_Find cache_find) {

		Object data = null;
		//1.获取key的值
		String key = getKey (joinPoint,cache_find);
		//2.根据key查询缓存
		String result = jedis.get(key);
		try {
		if(StringUtils.isEmpty(result)) {
			//如果结果为null，表示缓存中没有数据
			//查询数据
				data = joinPoint.proceed();
			//将数据转化为json串
				String json = ObjectMapperUtil.toJSON(data);
			//判断用户是否设置超时时间
			if(cache_find.secondes() == 0) 
				//表示不要超时
				jedis.set(key,json);
				else jedis.setex(key, cache_find.secondes(), json);
				System.out.println("第一次查询数据库");
		}else {
			Class tar= getClass(joinPoint);
			data =ObjectMapperUtil.toObject(result,tar);
			System.out.println("data"+data);
		}
			} catch (Throwable e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		return data;
	}
	
	//获取返回值的类型
	private Class getClass(ProceedingJoinPoint joinPoint) {
		MethodSignature signature=(MethodSignature) joinPoint.getSignature();
		return signature.getReturnType();
	}
	/**
	 * 1.判断用户key的类型
	 * @param joinPoint
	 * @param cache_find
	 * @return
	 */
	private String getKey
	(ProceedingJoinPoint joinPoint, Cache_Find cache_find) {
		//1.获取key的类型
		KEY_ENUM keytype = cache_find.keyType();
		//2.判断key的类型
		if(keytype.equals(KEY_ENUM.EMPTY)) {
			//表示使用用户自己的key
			return cache_find.key();
		}
		//表示用户的key需要拼接 key + "_"
		String strArgs = String.valueOf(joinPoint.getArgs()[0]);
		String key=cache_find.key()+"_"+strArgs;
		return key;
	}
	
}
