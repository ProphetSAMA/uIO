package fun.wsss.uio.Service;

import fun.wsss.uio.Entity.User;
import fun.wsss.uio.Mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
        return user;
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}