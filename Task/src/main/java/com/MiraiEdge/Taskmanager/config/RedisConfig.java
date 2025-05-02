package com.MiraiEdge.Taskmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.MiraiEdge.Taskmanager.Service.EmailNotificationService;


@Configuration
public class RedisConfig{

    @Bean
    RedisTemplate<String, EmailNotificationService> redisTemplate(
            RedisConnectionFactory connectionFactory) {
        RedisTemplate<String,EmailNotificationService> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(
        		EmailNotificationService.class));
        return template;
    }
}