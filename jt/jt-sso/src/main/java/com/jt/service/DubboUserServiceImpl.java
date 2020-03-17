package com.jt.service;

import java.rmi.dgc.DGC;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//dubbo实现类 实现中立接口
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;
@Service(timeout = 3000)
public class DubboUserServiceImpl implements DubboUserService{
	@Autowired
	private UserMapper usermapper;
	@Autowired
	private JedisCluster jedisCluster;
	@Transactional
	@Override
	public void saveUser(User user) {
		//密码加密
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		
		user.setEmail(user.getPhone());
		user.setCreated(new Date());
		user.setUpdated(user.getCreated());
		
		usermapper.insert(user);
		
	}
	
	/**
	 * 1.校验用户名密码是否正确 
	 * 	1.1正确则生成加密密钥：MD5加密（JT_TICKET+username+当前毫秒数）
	 * 	1.2userDB数据转化为userJSON
	 * 	1.3保存到redis中 7天
	 * 	1.4返回token密钥
	 * 2.不正确 则返回null 抛出异常要经过dubbo容器，返回null则数据不正确
	 */
	@Override
	public String findUserByUP(User user) {
		String token = null;
		String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pass);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>(user);
		User userDB = usermapper.selectOne(queryWrapper); //将从数据库中查询到的用户信息给userDB
		//判断userDB是否为空 空则错误
		if(userDB!=null) {
			token = "JT_TICKET_"+userDB.getUsername()+ System.currentTimeMillis();
			token = DigestUtils.md5DigestAsHex(token.getBytes());//生成token密钥
			//脱敏操作
			userDB.setPassword("密码已脱敏");
			String userJSON = ObjectMapperUtil.toJSON(userDB);//对象转json  才能存到redis中
			jedisCluster.setex(token, 7*24*3600, userJSON);//将token存到redis 中保存7天

		}
		return token;
	}
}
