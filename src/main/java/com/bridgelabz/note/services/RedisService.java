package com.bridgelabz.note.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

@Service
public class RedisService {

	private static Jedis jedis = new Jedis();
	private static String TOKEN = "jwtToken";
	private static Logger logger = Logger.getLogger(RedisService.class);

	public static void setToken(String jwtToken, String clientId) {
		jedis.hset(clientId, TOKEN, jwtToken);
		logger.info("Token set in redis");
	}

	public static String getToken(String clientId) {
		logger.info("Getting token from redis");
		System.out.println(jedis.hget(clientId,TOKEN));
		return jedis.hget(clientId,TOKEN);
	}
	
	public static void deleteToken(String clientId) {
		logger.info("Deleting token from redis");
		jedis.hdel(clientId, TOKEN);
	}
}
