package com.jt.util;

import com.jt.pojo.User;
/**
 * 	ThreadLocal:本地线程变量
	作用:可以在同一个线程内实现数据共享
 */
public class UserThreadLocal {
	private static ThreadLocal<User> thread = new ThreadLocal<>();
	
	//新增数据
	public static void set(User user) {
		thread.set(user);
	}
	//获取数据
	public static User get() {
		return thread.get();
	}
	//使用threadlocal切记关闭 防止内存泄漏
	public static void remove() {
		thread.remove();
	}

}
