package com.tb.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.HttpResource;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tb.service.UserService;
import com.tb.vo.SysResult;

import redis.clients.jedis.ShardedJedis;
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ShardedJedis jedis;
	
	//实现用户信息的校验
	@RequestMapping("/check/{param}/{type}")
	public SysResult checkUser(
				@PathVariable String param,
				@PathVariable int type,
				String callback
				) {
		System.out.println("param:"+param);
		System.out.println("callback："+callback);
//		JSONPObject jsonPobject;
		try {
			boolean falg=userService.findCheckUser(param,type);
//			jsonPobject = new JSONPObject(callback,SysResult.success(falg));
			
			return SysResult.success(falg);
		} catch (Exception e) {
			e.printStackTrace();
//			jsonPobject = new JSONPObject(callback,SysResult.fail());
			return SysResult.fail();
		}
	}
	//"http://localhost:8093/user/query/" + _ticket,
	@RequestMapping("/query/{ticket}")
	@ResponseBody
	public JSONPObject ticket(@PathVariable String ticket , String callback,HttpServletResponse  response) {
		JSONPObject jsonpObject;
		//判断ticket是否为空
		if(StringUtils.isEmpty(ticket)) {
			jsonpObject=new JSONPObject(callback,SysResult.fail());
			return jsonpObject;
		}
		//判断key是否存在
		if(!jedis.exists(ticket)) {
			//如果ticket不为null 但是redis中没有改数据
			//则表示Cookie中的数据有误，删除cookie
			Cookie cookie = new Cookie("TB_TICKET", "");
			cookie.setMaxAge(0);
			cookie.setDomain("49.234.86.111");
			cookie.setPath("/");
			response.addCookie(cookie);
			jsonpObject=new JSONPObject(callback,SysResult.fail());
			return jsonpObject;
			}
		String hget = jedis.get(ticket);
		jsonpObject = 
				new JSONPObject(callback, SysResult.success(hget));
		return jsonpObject;
	
	}
	
	
	
}
