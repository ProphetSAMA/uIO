package fun.wsss.uio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("power_record")
public class PowerRecord {
    private Long id;
    private Integer roomId;
    private BigDecimal powerValue;
    private LocalDateTime recordTime;
    private BigDecimal changeValue;
} 