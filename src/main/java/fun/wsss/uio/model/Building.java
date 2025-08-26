package fun.wsss.uio.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("building")
public class Building {
    private Integer id;
    private Integer areaId;
    private Integer buildingNumber;
    private Integer totalFloors;
    private Integer roomsPerFloor;
} 