package fun.wsss.uio.mapper.user;

import fun.wsss.uio.model.user.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户Mapper接口
 *
 * @author Wsssfun
 */
@Mapper
public interface UserMapper {
    /**
     * 插入用户
     *
     * @param user 用户
     */
    @Insert("INSERT INTO user (username, password, building_id, floor_id, room_id) VALUES (#{username}, #{password}, #{buildingId}, #{floorId}, #{roomId})")
    void insert(User user);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户
     */
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);

    /**
     * 根据用户ID查询用户
     *
     * @param id 用户ID
     * @return 用户
     */
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findByUserId(@Param("id") Long id);

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    @Select("SELECT * FROM user")
    List<User> getAllUser();
}
