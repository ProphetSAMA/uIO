package fun.wsss.uio.Service.room.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.wsss.uio.Entity.room.Room;
import fun.wsss.uio.Mapper.room.RoomMapper;
import fun.wsss.uio.Service.room.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 房间Service实现类
 * @author Wsssfun
 */
@Service
public class RoomServiceImpl implements IRoomService {
    @Autowired
    private RoomMapper roomMapper;

    @Override
    public List<Room> getRoomsByFloorId(Long floorId) {
        return roomMapper.selectList(new QueryWrapper<Room>().eq("floor_id", floorId));
    }

    @Override
    public Room getRoom(Long id) {
        return null;
    }

    @Override
    public List<Room> getRooms(Long id) {
        return List.of();
    }
}