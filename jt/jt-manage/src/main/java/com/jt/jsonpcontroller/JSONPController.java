package com.jt.jsonpcontroller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.util.ObjectMapperUtil;

@RestController
public class JSONPController {
	
	@RequestMapping("/web/testJSONP")
	public JSONPObject object(String callback) {
		com.jt.pojo.User user = new com.jt.pojo.User();
		//user.setId(11);
		//user.setName("jsonp");
		JSONPObject object = new JSONPObject(callback, user);
		return object;
	}
	
	//@RequestMapping("/web/testJSONP")
	//http://manage.jt.com/web/testJSONP?callback=jQuery111105616636006363949_1582269889283&_=1582269889284
	public String TestJSONP(String callback) {
		com.jt.pojo.User user = new com.jt.pojo.User();
		//user.setId(1);
		//user.setName("jsonp");
		String json = ObjectMapperUtil.toJSON(user);
		return callback +"("+json+")";
	}
}
