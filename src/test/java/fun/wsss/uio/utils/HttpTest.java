package fun.wsss.uio.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class HttpTest {

    @Autowired
    private Http http;

    @Test
    void testSendPostRequest() {
        // 调用 sendPostRequest 方法并验证结果
        String response = String.valueOf(http.queryPower(2, 1, 3, 7301));
        assertNotNull(response);
        assertTrue(response.contains("businessData"));
    }
}