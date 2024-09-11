package fun.wsss.uio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("fun.wsss.uio.mapper")
@EnableScheduling
public class UIoApplication {

    public static void main(String[] args) {
        SpringApplication.run(UIoApplication.class, args);
    }

}
