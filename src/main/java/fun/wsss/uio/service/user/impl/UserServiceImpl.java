package fun.wsss.uio.service.user.impl;

import fun.wsss.uio.mapper.user.UserMapper;
import fun.wsss.uio.model.user.User;
import fun.wsss.uio.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

/**
 * 用户Service实现类
 *
 * @author Wsssfun
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * JWT 密钥，使用安全密钥生成
     */
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     * 若 selectedRoom 为 null 或者长度小于 3 则抛出 IllegalArgumentException
     *
     * @param username     用户名
     * @param password     密码
     * @param selectedRoom 选择的房间
     */
    @Override
    public void register(String username, String password, List<Integer> selectedRoom) {
        int roomSize = 3;
        // 确保 selectedRoom 至少有三个值
        if (selectedRoom == null || selectedRoom.size() < roomSize) {
            throw new IllegalArgumentException("错误的房间信息");
        }

        // 创建 User 对象并设置属性
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setBuildingId(selectedRoom.get(0));
        user.setFloorId(selectedRoom.get(1));
        user.setRoomId(selectedRoom.get(2));

        // 执行插入操作
        userMapper.insert(user);
    }

    /**
     * 用户登录
     * 若用户名和密码匹配则生成 JWT 令牌，否则返回 null
     *
     * @param username 用户名
     * @param password 密码
     * @return JWT 令牌
     */
    @Override
    public String login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // 登录成功，生成 JWT 令牌
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        } else {
            return null;
        }
    }

    @Override
    public User getUserProfile(Long userId) {
        return userMapper.findByUserId(userId);
    }
}
