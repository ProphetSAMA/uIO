package fun.wsss.uio;

import fun.wsss.uio.utils.Http;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UIoApplicationTests {

    @Test
    void contextLoads() {
        Http http = new Http();
        System.out.println(http.sendPostRequest());
    }

}
