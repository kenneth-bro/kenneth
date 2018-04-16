package com.kenneth.boot.util.cache.redis;

import static org.hamcrest.CoreMatchers.nullValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Jedis连接池管理器
 * 
 * @author liq
 * @date 2018年3月26日
 *
 */
@Component("redisManager")
public class JedisPoolManager {
	
	Logger logger = LoggerFactory.getLogger(JedisPoolManager.class);

	@Autowired
	private JedisPool jedisPool;
	
	//默认存储过期时间：24h（单位:s）
	private static final int SRORAGE_TIMEOUT = 24 * 60 * 60;
	
	/**
	 * 设置key-value到redis
	 * 
	 * @author liq
	 * @date 2018年3月26日
	 * @param key
	 * @param value
	 * @param seconds
	 */
	public void setValue(String key, String value, int seconds){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			//过期时间
			if(seconds < 0){
				if(seconds == -1){
					jedis.expire(key, SRORAGE_TIMEOUT);
				}else{
					logger.info("[Jedis] 过期时间不能小于0,已忽略时间设置");
				}
			}else{
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			logger.error("[Jedis] set value is error." + e);
		}finally {
			//归还连接
			jedis.close();
		}
	}
	
	/**
	 * 从redis中获取value
	 * 
	 * @author liq
	 * @date 2018年3月26日
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		Jedis jedis = null;
		String result = null;
		try {
			jedis = jedisPool.getResource();
			result = jedis.get(key);
		} catch (Exception e) {
			logger.error("[Jedis] get value is error." + e);
		} finally {
			jedis.close();
		}
		return result;
	}
}
