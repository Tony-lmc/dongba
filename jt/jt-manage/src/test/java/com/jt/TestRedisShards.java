package com.jt;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
//redis分片
public class TestRedisShards {
	@Test
	public void testShards() {
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo> ();
		JedisShardInfo info1 = new JedisShardInfo("192.168.145.129", 6379);
		JedisShardInfo info2 = new JedisShardInfo("192.168.145.129", 6380);
		JedisShardInfo info3 = new JedisShardInfo("192.168.145.129", 6381);
		shards.add(info1);
		shards.add(info2);
		shards.add(info3);
		
		ShardedJedis shardedJedis = new ShardedJedis(shards);
		shardedJedis.set("username", "lmc");
		System.out.println(shardedJedis.get("username"));
	}
}