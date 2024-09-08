package fun.wsss.uio.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.wsss.uio.Entity.User;

public interface UserMapper extends BaseMapper<User> {
    User findByUsername(String username);
}
