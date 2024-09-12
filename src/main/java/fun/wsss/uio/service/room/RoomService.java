package fun.wsss.uio.service.room;

import fun.wsss.uio.model.room.Room;

import java.util.List;

/**
 * 房间Service接口
 * @author Wsssfun
 */
public interface RoomService {
    /**
     * 根据楼层ID获取房间列表
     * @param floorId 楼层ID
     * @return 房间列表
     */
    List<Room> getRoomsByFloorId(int floorId);
}

