package fun.wsss.uio.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * JSON工具类
 *
 * @author Wsssfun
 */
public class Json {
    public Double quantity;

    final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 处理HTTP响应并提取电量值
     *
     * @param response 响应内容
     */
    public void processResponse(String response) {
        if (response == null) {
            throw new IllegalArgumentException("HTTP响应为空");
        }

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(response);
        } catch (IOException e) {
            throw new RuntimeException("解析JSON失败", e);
        }

        JsonNode businessDataNode = jsonNode.get("businessData");
        if (businessDataNode == null) {
            throw new RuntimeException("JSON中找不到“businessData”字段");
        }

        JsonNode quantityNode = businessDataNode.get("quantity");
        if (quantityNode == null) {
            throw new RuntimeException("JSON中找不到“quantity”字段");
        }

        String quantityStr = quantityNode.textValue();

        try {
            this.quantity = Double.parseDouble(quantityStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException(""quantity"的数字格式无效", e);
        }
    }
}
