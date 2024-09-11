package fun.wsss.uio.Service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.wsss.uio.Entity.room.Room;
import fun.wsss.uio.Mapper.RoomMapper;
import fun.wsss.uio.Service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 房间实现类
 */
@Service
public class RoomServiceImpl implements IRoomService {
    @Autowired
    RoomMapper roomMapper;
    @Override
    public Room getRoom(Long id) {
        return null;
    }

    @Override
    public List<Room> getRooms(Long id) {
        Wrapper<Room> wrapper = new QueryWrapper<>();
        ((QueryWrapper<Room>) wrapper).eq("parent_id", id);
        return roomMapper.selectList(wrapper);
    }
}
