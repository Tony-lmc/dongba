package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	/*
	 * @Autowired private UserService userService;
	 */
	
	//dubbo注入
	@Reference(timeout = 3000,check = false)
	private DubboUserService userService;
	@Autowired
	private JedisCluster jedisCluster;
	
	//http://www.jt.com/user/login.html
	@RequestMapping("/{moduleName}")
	public String index(@PathVariable String moduleName) {
		return moduleName;
	}
	
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult saveUser(User user) {
		try {
			userService.saveUser(user);
			return SysResult.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return SysResult.fail();
		}
	}
	
	//单点用户登录
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult login(User user,HttpServletResponse response) {//js 里返回状态信息200 或者201
		try {
			String token = userService.findUserByUP(user);//接收sso后台传过来的token数据
			//返回的数据如果部位空 返回到cookie中  cookie中key为JT_TICKET(JS中规定)
			
			//实现cookie
			if(!StringUtils.isEmpty(token)) {
				Cookie cookie = new Cookie("JT_TICKET", token);//js中为JT_TICKET
				cookie.setMaxAge(7*24*3600);//7天超时
				cookie.setPath("/");
				cookie.setDomain("jt.com");//实现cookie共享
				response.addCookie(cookie);
				return SysResult.ok();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.fail();//如果没有正常运行则返回201报错
	}
	
	/**退出登录
	 * 1.删除缓存
	 * 2.删除cookie
	 * 
	 * @return  重定向页面
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();//拿到浏览器中的所有cookie
		if(cookies.length!=0) {//判断cookie是否为空
			String token = null;
			for (Cookie cookie : cookies) {//不为空则找 naame为JT_TICKET的cookie
				if(cookie.getName().equals("JT_TICKET")) {
					token = cookie.getValue();//拿到值token
					break;
				}
			}
			if(!StringUtils.isEmpty(token)) {
				jedisCluster.del(token);//删除token
				Cookie cookie = new Cookie("JT_TICKET", "");
				cookie.setMaxAge(0);// 时间0删除cookie
				cookie.setDomain("jt.com");
				cookie.setPath("/");
				response.addCookie(cookie);
			}
		}
		return "redirect:/";//当用户登出时,页面重定向到系统首页
	}

}
