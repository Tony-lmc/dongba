package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;

	/**
	 * 用户名校验  jsonp 跨域请求
	 * Request URL: http://sso.jt.com/user/check/1224asdas/1?r=0.25400509060637777&callback=jsonp1582294333897&_=1582294352754
	 */
	@RequestMapping("/check/{params}/{type}")// /1224asdas/1
	public JSONPObject checkUser(@PathVariable String params,@PathVariable Integer type,String callback) {
		JSONPObject jsonpobject = null;
		try {
			//返回true用户存在  false不存在
			Boolean flag = userService.checkUser(params,type);
			jsonpobject = new JSONPObject(callback, SysResult.ok(flag));

		} catch (Exception e) {
			e.printStackTrace();
			jsonpobject = new JSONPObject(callback, SysResult.fail());
		}
		return jsonpobject;
	}
	//httpClient.doPost(url, Map<K,V> getKey????);
	//http://sso.jt.com/user/register
	@RequestMapping("/register")
	public SysResult saveUser(String userJSON) {
		try {
			User user = ObjectMapperUtil.toObject(userJSON, User.class);
			userService.saveUser(user);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}

	@RequestMapping("/{moduleName}")
	public String index(@PathVariable String moduleName) {
		return moduleName;
	}

	//登录用户的回显
	@RequestMapping("/query/{ticket}")
	public JSONPObject findUserByTicket(@PathVariable String ticket,String callback) {
		String userJSON = jedisCluster.get(ticket);
		if(StringUtils.isEmpty(userJSON))
			//回传数据需要经过200判断 SysResult对象
			return new JSONPObject(callback, SysResult.fail());
		else 
			return new JSONPObject(callback, SysResult.ok(userJSON));
	}


}
