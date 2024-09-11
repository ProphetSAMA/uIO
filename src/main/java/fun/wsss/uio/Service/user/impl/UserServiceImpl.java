package fun.wsss.uio.Service.user.impl;

import fun.wsss.uio.Entity.User;
import fun.wsss.uio.Entity.room.Room;
import fun.wsss.uio.Mapper.UserMapper;
import fun.wsss.uio.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User user, Room room) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoomsid(room.getRoomId());
        userMapper.insert(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}