package fun.wsss.uio.model;

import lombok.Data;
import java.math.BigDecimal;

/**
 * 电量查询结果封装类
 * 
 * @author Wsssfun
 */
@Data
public class PowerQueryResult {
    /**
     * 查询状态码
     */
    private Integer code;
    
    /**
     * 查询消息
     */
    private String message;
    
    /**
     * 电量值
     */
    private BigDecimal value;
    
    /**
     * 房间号
     */
    private String roomNumber;
    
    /**
     * 是否查询成功
     */
    private boolean success;

    public PowerQueryResult() {
        this.success = false;
    }

    public static PowerQueryResult success(BigDecimal value, String roomNumber) {
        PowerQueryResult result = new PowerQueryResult();
        result.setCode(200);
        result.setValue(value);
        result.setRoomNumber(roomNumber);
        result.setSuccess(true);
        result.setMessage("查询成功");
        return result;
    }

    public static PowerQueryResult error(String message) {
        PowerQueryResult result = new PowerQueryResult();
        result.setCode(500);
        result.setMessage(message);
        result.setSuccess(false);
        return result;
    }
} 