package fun.wsss.uio.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fun.wsss.uio.model.PowerQueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * 发送HTTP请求的工具类
 *
 * @author Wsssfun
 */
@Slf4j
@Component
public class Http {
    private final RestTemplate restTemplate;
    
    @Value("${power.api.url:https://cloudpaygateway.59wanmei.com:8087/paygateway/smallpaygateway/trade}")
    private String apiUrl;
    
    @Value("${power.api.payproid:953}")
    private int payproId;
    
    @Value("${power.api.schoolcode:1402}")
    private String schoolCode;
    
    @Value("${power.api.timeout:5000}")
    private int timeout;

    public Http() {
        this.restTemplate = new RestTemplate();
    }

    @PostConstruct
    public void init() {
        validateConfig();
    }

    /**
     * 根据区域号查询电量
     * @param areaCode 区域号，格式如 "1-1" 或 "2-1"
     * @param floor 楼层
     * @param roomNumber 房间号
     * @return PowerQueryResult 查询结果
     */
    public PowerQueryResult queryPower(String areaCode, int floor, int roomNumber) {
        validateQueryParams(areaCode, floor, roomNumber);
        
        String[] areaParts = areaCode.split("-");
        return queryPower(
            Integer.parseInt(areaParts[0]), 
            Integer.parseInt(areaParts[1]), 
            floor, 
            roomNumber
        );
    }

    /**
     * 查询电量
     */
    public PowerQueryResult queryPower(int mainArea, int subArea, int floor, int roomNumber) {
        validateAreaParams(mainArea, subArea, floor, roomNumber);
        
        String roomVerify = formatRoomVerify(mainArea, subArea, floor, roomNumber);
        log.info("开始查询房间[{}]电量", roomVerify);
        
        try {
            String response = executeRequest(roomVerify);
            return parseResponse(response, roomVerify);
        } catch (Exception e) {
            log.error("房间[{}]电量查询异常: {}", roomVerify, e.getMessage(), e);
            return PowerQueryResult.error("电量查询失败: " + e.getMessage());
        }
    }

    @Retryable(
        value = {Exception.class},
        maxAttempts = 3,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    private String executeRequest(String roomVerify) {
        HttpHeaders headers = createHeaders();
        String bizContent = buildBizContent(roomVerify);
        JSONObject requestBody = buildRequestBody(bizContent);
        
        log.debug("请求参数: {}", requestBody.toJSONString());
        
        HttpEntity<String> request = new HttpEntity<>(requestBody.toJSONString(), headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(
            apiUrl,
            HttpMethod.POST,
            request,
            String.class
        );
        
        String response = responseEntity.getBody();
        log.debug("响应数据: {}", response);
        
        return response;
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private String buildBizContent(String roomVerify) {
        return String.format(
            "{\"payproid\":%d,\"schoolcode\":\"%s\",\"roomverify\":\"%s\",\"businesstype\":2}",
            payproId, schoolCode, roomVerify
        );
    }

    private JSONObject buildRequestBody(String bizContent) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("method", "samllProgramGetRoomState");
        requestBody.put("bizcontent", bizContent);
        return requestBody;
    }

    private String formatRoomVerify(int mainArea, int subArea, int floor, int roomNumber) {
        return String.format("%d-%d--%d-%d", mainArea, subArea, floor, roomNumber);
    }

    private PowerQueryResult parseResponse(String response, String roomVerify) {
        if (!StringUtils.hasText(response)) {
            return PowerQueryResult.error("响应数据为空");
        }
        
        try {
            JSONObject jsonResponse = JSON.parseObject(response);
            String code = jsonResponse.getString("code");
            String msg = jsonResponse.getString("msg");
            
            if (!"0000".equals(code)) {
                return PowerQueryResult.error(msg);
            }
            
            JSONObject data = jsonResponse.getJSONObject("data");
            if (data == null) {
                return PowerQueryResult.error("响应数据格式错误");
            }
            
            BigDecimal value = data.getBigDecimal("value");
            if (value == null) {
                return PowerQueryResult.error("电量数据为空");
            }
            
            return PowerQueryResult.success(value, roomVerify);
        } catch (Exception e) {
            log.error("响应数据解析失败: {}", e.getMessage(), e);
            return PowerQueryResult.error("响应数据解析失败");
        }
    }

    private void validateConfig() {
        if (!StringUtils.hasText(apiUrl)) {
            throw new IllegalStateException("API URL 未配置");
        }
        if (payproId <= 0) {
            throw new IllegalStateException("支付项目ID配置无效");
        }
        if (!StringUtils.hasText(schoolCode)) {
            throw new IllegalStateException("学校代码未配置");
        }
    }

    private void validateQueryParams(String areaCode, int floor, int roomNumber) {
        if (!StringUtils.hasText(areaCode)) {
            throw new IllegalArgumentException("区域号不能为空");
        }
        if (!areaCode.matches("\\d+-\\d+")) {
            throw new IllegalArgumentException("区域号格式无效");
        }
        validateFloorAndRoom(floor, roomNumber);
    }

    private void validateAreaParams(int mainArea, int subArea, int floor, int roomNumber) {
        if (mainArea <= 0 || subArea <= 0) {
            throw new IllegalArgumentException("区域号必须为正整数");
        }
        validateFloorAndRoom(floor, roomNumber);
    }

    private void validateFloorAndRoom(int floor, int roomNumber) {
        if (floor <= 0) {
            throw new IllegalArgumentException("楼层必须为正整数");
        }
        if (roomNumber <= 0) {
            throw new IllegalArgumentException("房间号必须为正整数");
        }
    }
}

class PowerQueryException extends RuntimeException {
    public PowerQueryException(String message) {
        super(message);
    }
    
    public PowerQueryException(String message, Throwable cause) {
        super(message, cause);
    }
}
