package com.yun.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;




import com.yun.dao.UserLoginMapper;
import com.yun.jedis.JedisClient;
import com.yun.pojo.Jsondata;
import com.yun.pojo.User;
import com.yun.util.JsonUtils;


@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${USER_SESSION}")
	private String USER_SESSION;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;
	
	
	@Autowired
	private UserLoginMapper userLogin;
	

	
	@Override
	public Jsondata UserLogin(User user) {
     
			User findbyname = userLogin.queryUserbyName(user.getUsername());
			if (findbyname == null) {
				/*jsondata.setStatus("0");
				jsondata.setData("the user is not exist ");*/
				return Jsondata.build("0", "the user is not exist");
				
			} else {
				User finduser = userLogin.queryUserForLogin(
						user.getUsername(), user.getPasswd());
			     	if (finduser == null) {

					/*jsondata.setStatus("0");
					jsondata.setData("the password is not right ");*/
					return Jsondata.build("0", "the password is not right");

			    	} else {
				//	jsondata.setStatus("1");

					//生成token，使用uuid
					String token = UUID.randomUUID().toString();
					//清空密码
				//	user.setPasswd(null);
					//把用户信息保存到redis，key就是token，value就是用户信息
					jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(finduser));
					//设置key的过期时间
					System.out.println(SESSION_EXPIRE);
					jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
				/*	jsondata.setToken(token);*/
					
					//jsondata.setData(finduser);
					//返回登录成功，其中要把token返回。
					//返回登录成功，其中要把token返回。
					return Jsondata.build("0", token);
					/*
					 * user.setActivation("1"); userService.updateStatus(user);
					 */

				
			}}

		}
	//	return jsondata;
		
		/*User findUser =userLogin.queryUserForLogin(Username, passwd);
		User findUser =userLogin.queryUserbyName(Username);
		return null;*/
	






	@Override
	public Jsondata Register(User user, MultipartFile pictureFile){
		//Jsondata jsondata = new Jsondata();
		User findbyname = userLogin.queryUserbyName(user.getUsername());
		if (findbyname != null) {

		/*	jsondata.setStatus("0");
			jsondata.setData("the user is  exist");*/
			return Jsondata.build("0", "the user is  exist");

		} else {
			
			String pictureFile_name =  pictureFile.getOriginalFilename();
			//新文件名称
			String newFileName = UUID.randomUUID().toString()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
			
			//上传图片
			File uploadPic = new java.io.File("F:/image/"+newFileName);
			
			if(!uploadPic.exists()){
				uploadPic.mkdirs();
				
			}
			//向磁盘写文件
			try {
				pictureFile.transferTo(uploadPic);
			}  catch (IOException e) {
				
				e.printStackTrace();
			}
			
		    user.setImage(newFileName);
			
		    userLogin.insertUser(user);
			/*jsondata.setStatus("1");
			jsondata.setData("register success");*/
		    return Jsondata.build("1", "register success");
		}

	//	return jsondata;
		
	
		
		
	}




	@Override
	public Jsondata getUserByToken(String token) {
		String json=jedisClient.get(USER_SESSION + ":" + token);
		if(StringUtils.isBlank(json)){
		//	jsondata.setStatus("1");
		//	jsondata.setData("the user is  overdue");
			return Jsondata.build("1", "the user is  overdue");
			
		}
		jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
		
		//把json转化成User对象
		User jsuserr=JsonUtils.jsonToPojo(json, User.class);
		  return Jsondata.build("1", jsuserr);
		  
	}

	/*@Override
	public Jsondata delToken(String token) {
		jedisClient.hdel(token, USER_SESSION);
		  return Jsondata.build("1", "the user loginout");
	}*/

	

}
