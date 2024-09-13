package fun.wsss.uio.service.room.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.wsss.uio.mapper.room.RoomMapper;
import fun.wsss.uio.model.room.Room;
import fun.wsss.uio.service.room.RoomService;
import fun.wsss.uio.utils.RoomFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 房间Service实现类
 *
 * @author Wsssfun
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    /**
     * 根据楼层ID获取房间列表
     *
     * @param floorId 楼层ID
     * @return 房间列表
     */
    @Cacheable(value = "roomCache", key = "#floorId")
    @Override
    public List<Room> getRoomsByFloorId(int floorId) {
        QueryWrapper<Room> wrapper = new QueryWrapper<>();
        wrapper.eq("floor_id", floorId);
        // 从数据库中获取房间列表
        List<Room> rooms = roomMapper.selectList(wrapper);

        // 使用工具类格式化房间信息
        return rooms.stream()
                .peek(room -> {
                    // 格式化房间显示名称
                    String formattedRoomDisplay = RoomFormatUtil.formatRoomDisplay(room.getFloorId(), room.getId());
                    room.setName(formattedRoomDisplay); // 更新房间名称为格式化后的名称
                })
                .collect(Collectors.toList());
    }
}
