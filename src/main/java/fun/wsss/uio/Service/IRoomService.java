package fun.wsss.uio.Service;

import fun.wsss.uio.Entity.room.Room;

import java.util.List;

/**
 * 房间接口
 * @author Wsssfun
 */
public interface IRoomService {

    List<Room> getRoomsByFloorId(Long floorId);

    /**
     * 获取房间
     * @param id 房间id
     * @return 房间
    */
    Room getRoom(Long id);

    List<Room> getRooms(Long id);
}
