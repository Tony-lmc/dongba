package com.jt;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

public class TestRedisCluster {
	@Test
	public void test01() {
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.145.129", 7000));
		nodes.add(new HostAndPort("192.168.145.129", 7001));
		nodes.add(new HostAndPort("192.168.145.129", 7002));
		nodes.add(new HostAndPort("192.168.145.129", 7003));
		nodes.add(new HostAndPort("192.168.145.129", 7004));
		nodes.add(new HostAndPort("192.168.145.129", 7005));
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("a", "集群搭建成功");
		System.out.println(cluster.get("a"));
	}
}
