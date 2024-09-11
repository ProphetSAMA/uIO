package fun.wsss.uio.Service;

import fun.wsss.uio.Entity.room.Room;

import java.util.List;

/**
 * 房间接口
 */
public interface IRoomService {

    Room getRoom(Long id);

    List<Room> getRooms(Long id);
}
