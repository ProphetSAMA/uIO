package fun.wsss.uio.Service;

import fun.wsss.uio.Entity.User;
import fun.wsss.uio.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    /**
     * 用户服务
     * Author: Wsssfun
     * Date: 2024年9月8日 23点54分
     */
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // 用户注册
    public User save(User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        return user;
    }

    // 用户登录
    // 根据用户名查找用户
    public User findByUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new RuntimeException("用户名不能为空");
        }
        if (userMapper.findByUsername(username) == null) {
            throw new RuntimeException("用户不存在");
        }

        return userMapper.findByUsername(username);
    }
}