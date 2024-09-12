package fun.wsss.uio.mapper.user;

import fun.wsss.uio.model.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper接口
 * @author Wsssfun
 */
@Mapper
public interface UserMapper {
    /**
     * 插入用户
     * @param user 用户
     */
    @Insert("INSERT INTO user (username, password, building_id, floor_id, room_id) VALUES (#{username}, #{password}, #{buildingId}, #{floorId}, #{roomId})")
    void insert(User user);
}

