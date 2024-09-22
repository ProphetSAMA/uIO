package fun.wsss.uio.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Redis配置类
 *
 * @author Wsssfun
 */
@Configuration
@EnableCaching
public class RedisConfig {

    /**
     * Redis缓存管理器配置
     * 针对不同数据设置不同的过期时间
     *
     * @param redisConnectionFactory Redis连接工厂
     * @return RedisCacheManager
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        // 默认缓存配置：7天过期
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(7))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        // 自定义缓存配置
        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        // 房间列表：永久缓存（不过期）
        cacheConfigurations.put("roomList", RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 永不过期
                .entryTtl(Duration.ZERO));

        // 全部电量信息：1小时过期
        cacheConfigurations.put("allPowerValue", RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 1小时过期
                .entryTtl(Duration.ofHours(1)));

        // 近七天电量信息：1小时过期
        cacheConfigurations.put("recentWeekPowerValue", RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                // 1小时过期
                .entryTtl(Duration.ofHours(1)));

        // 返回配置好的缓存管理器
        return RedisCacheManager.builder(redisConnectionFactory)
                // 默认配置
                .cacheDefaults(defaultCacheConfig)
                // 各个缓存的自定义配置
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    /**
     * RedisTemplate配置
     *
     * @param redisConnectionFactory Redis连接工厂
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用StringRedisSerializer序列化键
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // 使用GenericJackson2JsonRedisSerializer序列化值
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;
    }
}
