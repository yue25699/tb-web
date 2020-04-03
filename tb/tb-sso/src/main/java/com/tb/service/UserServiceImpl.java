package com.tb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tb.mapper.UserMapper;
import com.tb.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	/**
	 *  根据用户输入的信息判断
	 *  type
	 *  1 username
	 *  2 phone
	 *  3 email
	 *  
	 *  param 用户校验的信息
	 */
	@Override
	public boolean findCheckUser(String param, int type) {
		String column=type==1?"username":(type==2?"phone":"email");
//		QueryWrapper queryWrapper = new QueryWrapper();
//		queryWrapper.eq(column, param);
//		User user = userMapper.selectOne(queryWrapper);
		User user = userMapper.selectOnee(column,param);
		System.out.println(user);
		return user==null?false:true;
	}
	
	
	
}
