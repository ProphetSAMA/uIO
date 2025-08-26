package fun.wsss.uio.service.impl;

import fun.wsss.uio.mapper.RoomMapper;
import fun.wsss.uio.model.Room;
import fun.wsss.uio.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomMapper roomMapper;

    @Override
    public List<Room> getRoomsByBuildingId(Integer buildingId) {
        return roomMapper.findByBuildingId(buildingId);
    }

    @Override
    public List<Room> getRoomsByBuildingAndFloor(Integer buildingId, Integer floor) {
        return roomMapper.findByBuildingAndFloor(buildingId, floor);
    }

    @Override
    public Room getRoomById(Integer id) {
        return roomMapper.selectById(id);
    }
} 