package fun.wsss.uio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.wsss.uio.model.Room;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoomMapper extends BaseMapper<Room> {
    
    @Select("SELECT * FROM room WHERE building_id = #{buildingId}")
    List<Room> findByBuildingId(Integer buildingId);
    
    @Select("SELECT * FROM room WHERE building_id = #{buildingId} AND floor = #{floor}")
    List<Room> findByBuildingAndFloor(Integer buildingId, Integer floor);
} 