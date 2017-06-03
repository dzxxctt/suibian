package com.yun.control;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;





@Controller  
@RequestMapping("/mqtt") 
public class MqttControl {  
	@Autowired
    private MqttPahoMessageHandler mqtt;  

      
    @RequestMapping(value="/send") 
    @ResponseBody
    public void sendMessage(String topic,String data ,HttpServletResponse response) throws IOException{  
    	System.out.println("command打印");  
    	System.out.println("topic==="+topic);
    	System.out.println("data==="+data);
        Message<String> message = MessageBuilder.withPayload(data).setHeader(MqttHeaders.TOPIC, topic).build();  
        String f = "1";
        try {
			mqtt.handleMessage(message);
			System.out.println("成功");  
		} catch (Exception e) {
			e.printStackTrace();
			f = "0";
		}  
       
        response.getWriter().write(f);
    }  
}  