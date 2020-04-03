package com.tb.mapper;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tb.pojo.User;

public interface UserMapper extends BaseMapper<User>{

	@Select("select * from tb_user tu where tu.username =#{username} and tu.password = #{password}")
	User selectUser(String username, String password);

	@Select("select * from tb_user where ${column} = #{param}")
	User selectOnee(String column, String param);
	
}
