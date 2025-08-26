package fun.wsss.uio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("area")
public class Area {
    private Integer id;
    private Integer mainArea;
    private Integer subArea;
    private String name;
} 