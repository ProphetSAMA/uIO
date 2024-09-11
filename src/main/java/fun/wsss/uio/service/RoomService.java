package fun.wsss.uio.service;

import fun.wsss.uio.mapper.BuildingMapper;
import fun.wsss.uio.mapper.FloorMapper;
import fun.wsss.uio.mapper.RoomMapper;
import fun.wsss.uio.model.Building;
import fun.wsss.uio.model.Floor;
import fun.wsss.uio.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomService {

    @Autowired
    private RoomMapper roomMapper;

    public List<Room> getRoomsByBuildingAndFloor(Integer buildingId, Integer floorId) {
        return roomMapper.findRoomsByBuildingAndFloor(buildingId, floorId);
    }
}


