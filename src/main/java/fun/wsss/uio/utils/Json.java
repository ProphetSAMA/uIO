package fun.wsss.uio.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

/**
 * JSON工具类
 *
 * @author Wsssfun
 */
@Component
public class Json {
    public Double quantity;

    /**
     * 处理HTTP响应并提取电量值
     *
     * @param response HTTP响应字符串
     */
    public void processResponse(String response) {
        // 检查响应是否为空
        if (response == null) {
            throw new IllegalArgumentException("HTTP响应为空");
        }

        JSONObject jsonObject = JSONObject.parseObject(response);
        
        // 从JSON中提取businessData节点
        JSONObject businessData = jsonObject.getJSONObject("businessData");
        if (businessData == null) {
            throw new RuntimeException("JSON中找不到businessData字段");
        }

        // 从businessData节点中提取quantity字段
        String quantityStr = businessData.getString("quantity");
        if (quantityStr == null) {
            throw new RuntimeException("JSON中找不到quantity字段");
        }

        // 将提取到的值转换为Double类型
        try {
            this.quantity = Double.parseDouble(quantityStr);
        } catch (NumberFormatException e) {
            throw new RuntimeException("quantity的数字格式无效", e);
        }
    }

    public JSONObject parseResponse(String response) {
        // 检查响应是否为空
        if (response == null) {
            throw new IllegalArgumentException("HTTP响应为空");
        }
        return JSONObject.parseObject(response);
    }
}
