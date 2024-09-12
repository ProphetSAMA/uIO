package fun.wsss.uio.service.room.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.wsss.uio.mapper.room.RoomMapper;
import fun.wsss.uio.model.room.Room;
import fun.wsss.uio.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间Service实现类
 * @author Wsssfun
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Cacheable(value = "buildingCache", key = "#floorId")
    @Override
    public List<Room> getRoomsByFloorId(int floorId) {
        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("floor_id", floorId);
        return roomMapper.selectList(wrapper);
    }
}
