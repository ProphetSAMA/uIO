package fun.wsss.uio.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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

    @TableId(type = IdType.AUTO)
    private Long id;
    private Double value;
    private String querytime;
    @TableField("change_value")
    private Double changeValue;
    @TableField("room_verify")
    private String roomVerify;

    public Power() {
    }

    public Power(Double powerValue, String queryTime) {
        this.value = powerValue;
        this.querytime = queryTime;
    }


}
