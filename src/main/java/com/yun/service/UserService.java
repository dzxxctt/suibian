package com.yun.service;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.multipart.MultipartFile;

import com.yun.pojo.Jsondata;
import com.yun.pojo.User;

public interface UserService {
	public Jsondata UserLogin(User user);

	public Jsondata Register(User user, MultipartFile pictureFile);
	public Jsondata getUserByToken(String token);
	//public Jsondata delToken(String token);

}
