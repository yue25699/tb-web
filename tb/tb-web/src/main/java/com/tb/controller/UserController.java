package com.tb.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.tb.pojo.User;
import com.tb.service.DubboUserService;
import com.tb.vo.SysResult;

import redis.clients.jedis.ShardedJedis;

@RequestMapping("/user")
@Controller
@CrossOrigin
public class UserController {
	
	@Reference(timeout = 3000,check = true)
	private DubboUserService dubboUserService;
	
	@Autowired
	private ShardedJedis shardedJedis;
	
	@RequestMapping("/{moudel}")
	public String login(@PathVariable String moudel) {
		return moudel;
	}
	
	/**
	 * 实现用户信息的新增
	 * 
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult insertUser(User user) {
		dubboUserService.insertUser(user);
		return SysResult.success();
	}
	
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(HttpServletRequest request,User user,HttpServletResponse responce) {
		//1.获取服务器加密密匙
		System.out.println(user);
		String ticket=dubboUserService.doLoginn(user);
		if(StringUtils.isEmpty(ticket)) {
			return SysResult.fail();
		}
		//2.需要的将数据保存到cookie中
		Cookie cookie = new Cookie("TB_TICKET",ticket);
		System.out.println("已将cookie存入浏览器");
		cookie.setMaxAge(7*24*3600);
		//设定cookie的使用权限
		cookie.setPath("/");
		cookie.setDomain("49.234.86.111");
		responce.addCookie(cookie);
		System.out.println("发送成功！！！");
		
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length>0){
			for (Cookie cook : cookies) {
				if("TB_TICKET".equals(cook.getName())) {
					System.out.println("浏览器中获取cookie成功");
				}
			}
		}
		return SysResult.success();
	}
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//1.获取Cookie信息
		Cookie cookie = null;
		Cookie[] cookies = 
				request.getCookies();
		if(cookies!=null && cookies.length>0){
			for (Cookie cook : cookies) {
				if("TB_TICKET".equals(cook.getName())) {
					cookie=cook;
				}
			}
		}
		if(cookie == null) {
			//重定向到系统首页
			return "redirect:/";
		}
		
		//2.删除redis中的数据
		String ticket = cookie.getValue();
		shardedJedis.del(ticket);
		
		//3.利用工具API实现Cookie删除
		Cookie cookie2 = new Cookie("TB_TICKET","");
		cookie2.setMaxAge(0);
		cookie2.setDomain("49.234.86.111");
		cookie2.setPath("/");
		response.addCookie(cookie2);
		//重定向到系统首页
		return "redirect:/";
	}
	
	
}
