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
                .allowedOrigins(
                        "https://uio.ink",
                        "https://api.uio.ink",
                        "https://117.72.10.41",
                        "http://127.0.0.1",
                        "http://localhost",
                        "http://localhost:5173",
                        "https:127.0.0.1",
                        "https://localhost",
                        "https://localhost:8080")
                // 允许所有请求方法
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                // 允许所有请求头
                .allowedHeaders("*")
                // 允许跨域请求携带cookie
                .allowCredentials(true);
    }
}