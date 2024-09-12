package fun.wsss.uio.model.room;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 楼层实体类
 * @author Wsssfun
 */
@Setter
@Getter
@Data
@TableName("floor")
public class Floor {
    private Integer id;
    private Integer buildingId;
    private String name;

}
