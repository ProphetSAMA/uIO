package fun.wsss.uio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 电量实体类
 *
 * @author Wsssfun
 */
@Setter
@Getter
@TableName("power")
public class Power {
    private Long id;
    private Integer mainArea;
    private Integer subArea;
    private Integer floor;
    private Integer roomNumber;
    private BigDecimal value;
    private LocalDateTime querytime;
    private Double changeValue;
}
