package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;

/**
 * 用户未登录使用购物车时，从重定向到登录页面
 * 重写prehandle方法  返回值true方向 false拦截
 * 
 *
 */
@Component
public class UserInterceptor implements HandlerInterceptor{
	@Autowired
	private JedisCluster jedisCluster;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//1.获取cookie信息 有token则放行
		String token = null;
		Cookie[] cookies = request.getCookies();//获取cookie
		for (Cookie cookie : cookies) {
			if("JT_TICKET".equals(cookie.getName())) {
				token = cookie.getValue();//遍历cookie 找到name未JT_TICKET的cookie 
				break;
			}
		}
		//2.与redis中cookie校验
		if(!StringUtils.isEmpty(token)) {
			//判断redis中是否有该token
			String userJSON = jedisCluster.get(token);
			if(!StringUtils.isEmpty(userJSON)) {//redis中有用户数据.拦截放行
				User user = ObjectMapperUtil.toObject(userJSON, User.class);
				//将user数据保存到request域中.
				//request.setAttribute("JT_USER",user);
				
				UserThreadLocal.set(user);
				return true;
			}

		}
		//重定向到登录页面
		response.sendRedirect("/user/login.html");
		return false;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//request.getSession().removeAttribute("JT_USER");
		//调用移除操作
		UserThreadLocal.remove();
	}

}
