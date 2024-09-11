package fun.wsss.uio.Mapper.room;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.wsss.uio.Entity.room.Room;
import org.apache.ibatis.annotations.Mapper;

/**
 * 房间Mapper接口
 * @author Wsssfun
 */
@Mapper
public interface RoomMapper extends BaseMapper<Room> {
}