package com.kenneth.boot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置
 * Spring Session 需要redis 2.8及其以上版本，然后开启事件通知，在redis配置文件中加上 notify-keyspace-events Ex
 * Keyspace notifications功能默认是关闭的（默认地，Keyspace 时间通知功能是禁用的，因为它或多或少会使用一些CPU的资源）。
 * 或是使用如下命令：  
 * redis-cli config set notify-keyspace-events Egx  
 * 如果你的Redis不是你自己维护的，比如你是使用阿里云的Redis数据库，你不能够更改它的配置，那么可以使用如下方法：在applicationContext.xml中配置  
 * <util:constant static-field="org.springframework.session.data.redis.config.ConfigureRedisAction.NO_OP"/>  
 * @author liq
 * @date 2018年3月26日
 *
 */
@Configuration
@EnableRedisHttpSession
public class RedisConfig {
	Logger logger = LoggerFactory.getLogger(RedisConfig.class);
	
	@Value("${spring.redis.host}")
	private String host;
	
	@Value("${spring.redis.port}")
	private int port;
	
	@Value("${spring.redis.timeout}")
	private int timeout;
	
	@Value("${spring.redis.pool.max-idle}")
	private int maxIdle;
	
	@Value("${spring.redis.pool.max-wait}")
	private long maxWaitMillis;
	
	@Value("{spring.redis.password}")
	private String password;
	
	@Bean
	public ConfigureRedisAction configgureRedisAction(){
		return ConfigureRedisAction.NO_OP;
	}
	
	/**
	 * 初始化连接池
	 * 
	 * @author liq
	 * @date 2018年3月26日
	 * @return
	 */
	@Bean
	public JedisPool redisPoolFactory(){
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(maxIdle);
		jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
		JedisPool jedisPool = null;
		if(password != null && !"".equals(password)){
			//使用密码校验
			jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
		}else{
			//无密码
			jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout);
		}
		if(jedisPool != null){
			logger.info("[Jedis] JedisPool init success.host at " + host + ":" + port);
		}
		return jedisPool;
	}
	
}
