package fun.wsss.uio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.wsss.uio.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    
    @Select("SELECT * FROM users WHERE username = #{username}")
    User findByUsername(String username);
    
    @Select("SELECT * FROM users WHERE building_id = #{buildingId} AND floor_id = #{floorId} AND room_id = #{roomId}")
    User findByLocation(Long buildingId, Long floorId, Long roomId);
} 