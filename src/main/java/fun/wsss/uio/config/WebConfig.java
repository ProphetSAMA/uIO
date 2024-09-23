package fun.wsss.uio.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 *
 * @author Wsssfun
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 跨域配置
     *
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许跨域请求的路径
        registry.addMapping("/**")
                // 允许跨域请求的域名
                .allowedOrigins("http://localhost:5173", "http://uio.ink:80","http://localhost:8080", "117.72.10.41" ,"117.72.10.41:80")
                // 允许所有请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许所有请求头
                .allowedHeaders("*")
                // 允许跨域请求携带cookie
                .allowCredentials(true);
    }
}