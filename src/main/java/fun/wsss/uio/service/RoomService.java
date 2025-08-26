package fun.wsss.uio.service;

import fun.wsss.uio.model.Room;
import java.util.List;

public interface RoomService {
    List<Room> getRoomsByBuildingId(Integer buildingId);
    List<Room> getRoomsByBuildingAndFloor(Integer buildingId, Integer floor);
    Room getRoomById(Integer id);
} 