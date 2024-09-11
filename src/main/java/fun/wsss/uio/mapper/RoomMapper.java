package fun.wsss.uio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import fun.wsss.uio.model.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 房间Mapper接口
 * @author Wsssfun
 */
@Mapper
public interface RoomMapper {
    @Select("SELECT * FROM room WHERE building_id = #{buildingId} AND floor_id = #{floorId}")
    List<Room> findRoomsByBuildingAndFloor(@Param("buildingId") Integer buildingId, @Param("floorId") Integer floorId);
}



