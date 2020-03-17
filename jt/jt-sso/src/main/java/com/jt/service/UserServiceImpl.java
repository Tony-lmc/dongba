package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectCount;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.vo.SysResult;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	/**
	 * 需要将type转化为字段 1：用户名 2：手机号 3：邮箱
	 */
	@Override
	public Boolean checkUser(String params, Integer type) {
		String column = //三目运算符判断
				type==1?"username":(type==2?"phone":"email");
		//查询数据库是否有此数据 column
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(column, params);
		int count = userMapper.selectCount(queryWrapper);
		return count==0?false:true;//返回true用户存在  false不存在
	}

	@Transactional	//事务控制
	@Override
	public void saveUser(User user) {
		user.setEmail(user.getPhone())
			.setCreated(new Date())
			.setUpdated(user.getCreated());
		userMapper.insert(user);
		//int a = 1/0; //测试当用户入库失败后用户展现情况
	}
	


}
