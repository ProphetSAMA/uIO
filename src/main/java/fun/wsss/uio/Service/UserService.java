package fun.wsss.uio.Service;

import fun.wsss.uio.Entity.User;
import fun.wsss.uio.Entity.room.Room;
import fun.wsss.uio.Mapper.RoomMapper;
import fun.wsss.uio.Mapper.UserMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {
    /**
     * 用户服务
     * Author: Wsssfun
     * Date: 2024年9月8日 23点54分
     */
    Logger logger = Logger.getLogger("UserService");
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    // 用户注册
//    public User save(@NotNull User user, Room room) {
//        if (user.getPassword() == null || user.getPassword().isEmpty()) {
//            throw new RuntimeException("密码不能为空");
//        }
//        if (userMapper.findByUsername(user.getUsername()) != null) {
//            throw new RuntimeException("用户名已存在");
//        }
//        // 密码加密
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        // 插入用户
//        userMapper.insert(user);
//        // 设置用户的房间id
//        user.setRoomsid(room.getRoomsid());
//        userMapper.updateById(user);
//        logger.info("用户注册成功");
//        return user;
//    }

    // 用户登录
    // 根据用户名查找用户
    public User findByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        if (userMapper.findByUsername(username) == null) {
            throw new RuntimeException("用户不存在");
        }

        logger.info("用户登录成功");
        return userMapper.findByUsername(username);
    }
}