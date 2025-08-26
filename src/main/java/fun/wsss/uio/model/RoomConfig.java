package fun.wsss.uio.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房间配置实体类
 *
 * @author Wsssfun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomConfig {
    /**
     * 建筑ID
     */
    private int buildingId;
    
    /**
     * 楼层ID
     */
    private int floorId;
    
    /**
     * 房间ID
     */
    private int roomId;

    public RoomConfig(int mainArea, int subArea, int floor, int roomNumber) {
    }

    // Getters and Setters
    public int getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
} 