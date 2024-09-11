package fun.wsss.uio.Service.user;

import fun.wsss.uio.Entity.User;
import fun.wsss.uio.Entity.room.Room;

/**
 * 用户Service接口
 * @author Wsssfun
 */
public interface UserService {
    User save(User user, Room room);
    User findByUsername(String username);
}