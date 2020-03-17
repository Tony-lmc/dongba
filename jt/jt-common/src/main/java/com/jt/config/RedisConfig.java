package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

//表示redis配置类
@Configuration //xml
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	@Value("${redis.nodes}")
	private String redisNodes;
	
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		//讲pro文件中ip和节点信息拆分
		String[] strNode = redisNodes.split(",");
		for(String node : strNode) {
			//拆分拿到host和port
			String host = node.split(":")[0];
			int port = Integer.parseInt(node.split(":")[1]);
			HostAndPort hap = new HostAndPort(host, port);
			nodes.add(hap);
		}
		return new JedisCluster(nodes);
	}
	
	
	
	
	
	
	/*redis 哨兵
	@Value("${redis.masterName}")
	private String masterName;
	@Value("${redis.sentinels}")
	private String sentinelsNodes;
	@Bean
	public JedisSentinelPool jedisSentinelPool() {
		Set<String> sentinels = new HashSet<String>();
		sentinels.add(sentinelsNodes);
		return new JedisSentinelPool(masterName, sentinels);
	}
	*/
	
	
	/*redis分片
	 * @Value("${redis.nodes}") private String redisNodes; //node1,node2,node3
	 * 
	 * @Bean //<bean id="" class=""> public ShardedJedis shardedJedis() {
	 * List<JedisShardInfo> shards = new ArrayList<>(); //ip:端口,ip:端口 String[] nodes
	 * = redisNodes.split(","); for (String node : nodes) { String host =
	 * node.split(":")[0]; int port = Integer.parseInt(node.split(":")[1]);
	 * JedisShardInfo info = new JedisShardInfo(host, port); shards.add(info); }
	 * 
	 * return new ShardedJedis(shards); }
	 */
}	
	
	/*
	 * @Value("${jedis.host}") private String host;
	 * 
	 * @Value("${jedis.port}") private Integer port;
	 * 
	 * @Bean public Jedis jedis() {
	 * 
	 * return new Jedis(host, port); }
	 */

