package com.yun.dao;

import com.yun.pojo.User;

public interface UserLoginMapper {

	public User queryUserbyName(String username);	
	public User queryUserForLogin(String username,String passswd);	
	public void insertUser(User user);
	
	
	
	
}
