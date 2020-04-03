package com.tb.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.druid.util.StringUtils;
import com.tb.pojo.User;
import com.tb.util.ObjectMapperUtil;
import com.tb.util.UserThreadLocal;

import redis.clients.jedis.ShardedJedis;

@Component
public class UserInterceptor implements HandlerInterceptor {
	@Autowired
	private ShardedJedis jedis;
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		System.out.println("进入拦截器");
		String ticket = null;
		System.out.println("获取cookies：" + cookies);
		if(cookies.length>0) {
			for (Cookie cookie : cookies) {
				System.out.println("输出cookie："+cookie.getName());
				if("TB_TICKET".equals(cookie.getName())) {
					ticket = cookie.getValue();
					break;
				}
			}
		}
		if(! StringUtils.isEmpty(ticket)) {
			String string = jedis.get(ticket);
			if(! StringUtils.isEmpty(string)) {
				User object = ObjectMapperUtil.toObject(string, User.class);
				UserThreadLocal.set(object);
				System.out.println("不拦截");
				return true;//表示不拦截
			}
		}
		//重定向到登录页面
		response.sendRedirect("/user/login.html");
		
		return false;//false 表示拦截
	}
	
	/**
	 * 删除拦截器 防止内存泄露
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove();
	}
}
