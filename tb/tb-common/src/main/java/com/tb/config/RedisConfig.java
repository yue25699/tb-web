package com.tb.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
//	@Value("${jedis.host}")
//	private String host;
//
//	@Value("${jedis.port}")
//	private Integer port;

	@Value("${jedis.nodes}")
	private String nodes;

	//将redis交给spring管理
//	@Bean
//	public Jedis jedis() {
//		return new Jedis(host,port);
//	}

	//将redis分片交给spring管理

	
	
	
	//鞋鞋+鞋鞋+鞋鞋=30
	//鞋鞋=10   鞋=5
	//光头+光头+鞋鞋=20
	//光头=5
	//屎屎+屎屎+光头=13
	//屎屎=4  屎=2
	//鞋+光头+屎=7


	@Bean
	@Scope("prototype") 
	public ShardedJedis shardedJedis() { 
		List<JedisShardInfo> shards = 
				new ArrayList<JedisShardInfo>(); 
		String[] redisNodes =nodes.split(",");
		for (String redisNode : redisNodes) { //redisNode=IP:PORT
		String[] hostAndPort = redisNode.split(":"); 
		String host = hostAndPort[0];
		int port = Integer.parseInt(hostAndPort[1]); 
		JedisShardInfo info = new
				JedisShardInfo(host, port); 
		shards.add(info);
		} 
		return new ShardedJedis(shards);
		}
}
