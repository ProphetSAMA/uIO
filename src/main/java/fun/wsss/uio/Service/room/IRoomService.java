package fun.wsss.uio.Service.room;

import fun.wsss.uio.Entity.room.Room;
import java.util.List;

public interface IRoomService {
    List<Room> getRoomsByFloorId(Long floorId);
    Room getRoom(Long id);
    List<Room> getRooms(Long id);
}