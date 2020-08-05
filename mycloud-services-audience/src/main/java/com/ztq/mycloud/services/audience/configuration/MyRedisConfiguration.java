package com.ztq.mycloud.services.audience.configuration;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableCaching
public class MyRedisConfiguration {
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)throws UnknownHostException {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		//key采用string序列化
		template.setKeySerializer(stringRedisSerializer());
		template.setHashKeySerializer(stringRedisSerializer());
		//value采用json序列化
		template.setValueSerializer(jsonRedisSerializer());
		template.setHashValueSerializer(jsonRedisSerializer());
		template.afterPropertiesSet();
		return template;
	}
	
	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        		// 设置缓存的默认过期时间
        config = config.entryTtl(Duration.ofMinutes(10))
                // key采用string序列化
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer()))
                // value采用json序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer()))
                // 不缓存空值
                .disableCachingNullValues();
        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("busy");
        cacheNames.add("idle");
        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("busy", config.entryTtl(Duration.ofSeconds(60)));
        configMap.put("idle", config);
        RedisCacheManager cacheManager = RedisCacheManager.builder(redisConnectionFactory)
                .initialCacheNames(cacheNames)
                .withInitialCacheConfigurations(configMap)
                .build();
        return cacheManager;
	}
	
	@Bean
	//string序列化器
	public StringRedisSerializer stringRedisSerializer() {
		return new StringRedisSerializer();
	}
	
	@Bean
	//json序列化器
	public Jackson2JsonRedisSerializer<Object> jsonRedisSerializer(){
		Jackson2JsonRedisSerializer<Object> jsonSerializer=new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper objectMapper=new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jsonSerializer.setObjectMapper(objectMapper);
		return jsonSerializer;
	}
}