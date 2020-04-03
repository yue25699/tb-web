package com.tb.service;

import com.tb.pojo.User;

public interface DubboUserService {

	//暂时为空 搭建dubbo框架
	void insertUser(User user);

	String doLoginn(User user);

	boolean selectOne(String param, int type);


	
}
