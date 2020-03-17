package com.jt;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class TestRedis {
	
	@Test
	public void testString() {
		Jedis jedis = new Jedis("192.168.145.129", 6379);
		jedis.set("username", "lmc");
		System.out.println("jedis中的数据："+jedis.get("username"));
	}
}
