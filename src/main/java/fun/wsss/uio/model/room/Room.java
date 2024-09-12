package fun.wsss.uio.model.room;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 房间实体类
 *
 * @author Wsssfun
 */
@Getter
@Setter
@Data
@TableName("room")
public class Room {
    private Integer id;
    private Integer floorId;
    private String name;
}

