package fun.wsss.uio.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 电量实体类
 *
 * @author Wsssfun
 */
@Setter
@Getter
public class Power {

    private Long id;
    private Long userId;
    private Double value;
    private String querytime;
    @TableField("change_value")
    private Double changeValue;

    public Power() {
    }

    public Power(Double powerValue, String queryTime) {
        this.value = powerValue;
        this.querytime = queryTime;
    }

    public Power(Long userId, Double powerValue, String queryTime) {
        this.userId = userId;
        this.value = powerValue;
        this.querytime = queryTime;
    }

}
