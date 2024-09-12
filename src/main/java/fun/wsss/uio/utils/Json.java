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
     * @param http HTTP响应对象
     */
    public void processResponse(Http http) {
        // 检查HTTP响应是否为空
        if (http == null || http.response == null) {
            throw new IllegalArgumentException("HTTP响应为空");
        }

        JsonNode jsonNode;
        try {
            // 读取并解析HTTP响应体为JsonNode对象
            jsonNode = objectMapper.readTree(http.response);
        } catch (IOException e) {
            throw new RuntimeException("解析JSON失败", e);
        }

        // 从JSON中提取businessData节点
        JsonNode businessDataNode = jsonNode.get("businessData");
        if (businessDataNode == null) {
            throw new RuntimeException("JSON中找不到“businessData”字段");
        }

        // 从businessData节点中提取quantity节点
        JsonNode quantityNode = businessDataNode.get("quantity");
        if (quantityNode == null) {
            throw new RuntimeException("JSON中找不到“quantity”字段");
        }

        // 获取quantity节点的文本值
        String quantityStr = quantityNode.textValue();

        // 将提取到的值从String类型转换为Double类型，并赋值给类的成员变量
        try {
            this.quantity = Double.parseDouble(quantityStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("“quantity”的数字格式无效", e);
        }
    }
}
