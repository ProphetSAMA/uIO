package fun.wsss.uio.utils;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 发送HTTP请求的工具类
 *
 * @author Wsssfun
 */
public class Http {

    /**
     * 发送POST请求查询房间电量状态
     *
     * @param roomVerify 房间验证标识
     * @return 响应内容
     */
    public String sendPostRequest(String roomVerify) {
        String result;
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("https://cloudpaygateway.59wanmei.com:8087/paygateway/smallpaygateway/trade");
            post.setHeader("User-Agent", "Mozilla/5.0 (Linux; Android 11; SAMSUNG SM-G973U) AppleWebKit/537.36 (KHTML, like Gecko) SamsungBrowser/14.2 Chrome/87.0.4280.141 Mobile Safari/537.36");
            post.setHeader("Accept", "application/json, text/plain, */*");
            post.setHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            post.setHeader("Origin", "https://cloudpaygateway.59wanmei.com:8087");
            post.setHeader("Connection", "keep-alive");
            post.setHeader("Referer", "https://cloudpaygateway.59wanmei.com:8087/pay/index.html?token=9d50babf-49b2-4c2f-a98f-f09a0d8e4857&_timestamp=1668578948720&customerId=1402");
            post.setHeader("Sec-Fetch-Dest", "empty");
            post.setHeader("Sec-Fetch-Mode", "cors");
            post.setHeader("Sec-Fetch-Site", "same-origin");
            post.setHeader("TE", "trailers");
            post.setHeader("Cookie", "SERVERID=b82512854b884d91ab4d85c59fa4706e|1668580570|1668579876");
            post.setHeader("Content-Type", "application/json;charset=utf-8");
            post.setHeader("Host", "cloudpaygateway.59wanmei.com:8087");

            String json = "{\"timestamp\":\"2022-11-16 14:35:33\",\"method\":\"samllProgramGetRoomState\",\"bizcontent\":\"{\\\"payproid\\\":953,\\\"schoolcode\\\":\\\"1402\\\",\\\"roomverify\\\":\\\"" + roomVerify + "\\\",\\\"businesstype\\\":2}\",\"sourceId\":2}";
            post.setEntity(new StringEntity(json));

            try (CloseableHttpResponse response = httpClient.execute(post)) {
                result = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                throw new RuntimeException("获取响应时出现问题：", e);
            }
        } catch (IOException e) {
            throw new RuntimeException("请求发送失败：", e);
        }
        return result;
    }
}
