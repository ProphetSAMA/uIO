package fun.wsss.uio.config;

import fun.wsss.uio.utils.Http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    public Http http() {
        return new Http();
    }
} 