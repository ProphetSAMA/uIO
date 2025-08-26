package fun.wsss.uio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("room")
public class Room {
    private Integer id;
    private Integer buildingId;
    private Integer floor;
    private Integer roomNumber;
    private Integer fullRoomNumber;
} 