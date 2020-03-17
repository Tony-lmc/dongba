package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

public class TestRedisSentinel {
	@Test
	public void test01() {//测试redis哨兵
		Set<String> sentinels = new HashSet<String>();
		sentinels.add("192.168.145.129:26379");//哨兵的ip和端口
		JedisSentinelPool jsp = new JedisSentinelPool("mymaster", sentinels);
		Jedis jedis = jsp.getResource();
		jedis.set("cc","sentinelOK");
		System.out.println(jedis.get("cc"));
		jedis.close();//归还连接
	}
}
