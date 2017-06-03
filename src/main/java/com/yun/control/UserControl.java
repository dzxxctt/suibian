package com.yun.control;



import java.awt.PageAttributes.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;






import com.yun.pojo.Jsondata;
import com.yun.pojo.User;
import com.yun.service.UserService;
import com.yun.util.CookieUtils;
import com.yun.util.JsonUtils;


@Controller
@RequestMapping("/user")
public class UserControl {
	
	
	
	
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	
	
	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	@ResponseBody
	public Jsondata login(User user,HttpServletResponse response,HttpServletRequest request) throws Exception {
		Jsondata result=userService.UserLogin(user);
		//把token 写入cookie
		CookieUtils.setCookie(request, response, "TOKEN_KEY", result.getData().toString());
		return result;
	
	}

	@RequestMapping("/register")
	@ResponseBody
	public Jsondata register(User user, MultipartFile pictureFile) throws Exception {
	
		Jsondata result=userService.Register(user, pictureFile);
		return result;
	}

	
	@RequestMapping(value="/token/{token}",method=RequestMethod.GET)
			//指定返回响应数据的content-type
		//	produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable String token,String callback){
		
		Jsondata result = userService.getUserByToken(token);
		//判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)){
			
			return callback +"("+JsonUtils.objectToJson(result)+");";
		}
	
		//return result;
		return JsonUtils.objectToJson(result);
		
	}
	
	
	
	
	
//JSONP的第二种方法 spring4.1以上版本使用
 /* public Object getUserByToken(@PathVariable String token,String callback){
		
		Jsondata result = userService.getUserByToken(token);
		//判断是否为jsonp请求
		if(StringUtils.isNotBlank(callback)){
			
			MappingJacksonValue mappingJacksonValue=new MappingJacksonValue(result);
	    mappingJacksonValue.setJsonpFunction(callback);
		return mappingJacksonValue ;
		}
		//return result;
		return result;
		
	}*/
	
	
	
	
	
	
	
	
	
	
	/*@RequestMapping("/upload")
	@ResponseBody
	public Jsondata upload(User user, MultipartFile pictureFile) throws Exception {
		Jsondata jsondata = new Jsondata();
		//原始文件名称
				String pictureFile_name =  pictureFile.getOriginalFilename();
				//新文件名称
				String newFileName = UUID.randomUUID().toString()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
				
				//上传图片
				File uploadPic = new java.io.File("F:/image/"+newFileName);
				
				if(!uploadPic.exists()){
					uploadPic.mkdirs();
					
				}
				//向磁盘写文件
				pictureFile.transferTo(uploadPic);
				
				jsondata.setStatus("1");
				jsondata.setData(" success");
		
		
				return jsondata;
			
		
	}
	*/
	 
	 
	
	
	
}
