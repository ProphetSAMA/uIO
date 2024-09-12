package fun.wsss.uio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * uIO启动类
 *
 * @author Wsssfun
 */
@SpringBootApplication
@MapperScan("fun.wsss.uio.mapper")
@EnableScheduling
@EnableCaching
public class UioApplication {

    public static void main(String[] args) {
        SpringApplication.run(UioApplication.class, args);
    }

}
