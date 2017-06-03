package com.yun.pojo;

import com.yun.util.TaotaoResult;


public class Jsondata {

	private String status;

	private Object data;

	
	

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public static Jsondata build(String status, Object data) {
        return new Jsondata(status,  data);
    }
	
	
	
	
	
	
	 public static Jsondata ok(Object data) {
	        return new Jsondata(data);
	    }

	    public static Jsondata ok() {
	        return new Jsondata(null);
	    }

	    public Jsondata() {

	    }

	    public Jsondata(String status, Object data) {
	        this.status = status;
	    
	        this.data = data;
	    }

	    public Jsondata(Object data) {
	        this.status = "1";
	        this.data = data;
	    }

	
	
	
}
