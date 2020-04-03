package com.tb.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tb.mapper.UserMapper;
import com.tb.pojo.User;
import com.tb.util.ObjectMapperUtil;

import redis.clients.jedis.ShardedJedis;

@Service(timeout = 3000) //将对象交给dubbo管理
public class DubboUserServiceImpl implements DubboUserService{
	
	@Autowired
	private ShardedJedis jedis;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public void insertUser(User user) {
		//密码加密
		
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5DigestAsHex).setUpdated(new Date()).setCreated(new Date());
		int insert = userMapper.insert(user);
		System.out.println(insert);
	}

	@Override
	public String doLoginn(User user) {
		
		String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5DigestAsHex);
		
		String username = user.getUsername();
		String password=md5DigestAsHex;
		User userDB = userMapper.selectUser(username,password);
		
//		 QueryWrapper<User> queryWrapper = new QueryWrapper();
//		 System.out.println(queryWrapper);
//		User userDB = userMapper.selectOne(queryWrapper);
//		
		System.out.println("userDB:  "+userDB);
		
		//ticket
		String ticket=null;
		if(userDB != null) {
			ticket =
					DigestUtils.md5DigestAsHex(UUID.randomUUID().toString().getBytes());
			userDB.setPassword("123456");
			String json = ObjectMapperUtil.toJSON(userDB);
			jedis.setex(ticket, 7*24*3600,json);
		}
		return ticket;
	}

	@Override
	public boolean selectOne(String param, int type) {
		String column=type==1?"username":(type==2?"phone":"email");
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq(column, param);
		User user = userMapper.selectOne(queryWrapper);
		System.out.println(user);
		return user==null?false:true;
	}

}
