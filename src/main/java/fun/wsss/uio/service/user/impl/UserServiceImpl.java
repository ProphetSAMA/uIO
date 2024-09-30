package fun.wsss.uio.service.user.impl;

import fun.wsss.uio.dto.user.UserDTO;
import fun.wsss.uio.mapper.user.UserMapper;
import fun.wsss.uio.model.user.User;
import fun.wsss.uio.service.user.UserService;
import fun.wsss.uio.utils.RoomFormatUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        user.setPassword(bCryptPasswordEncoder.encode(password));
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
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
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

    /**
     * 将 User 对象转换为 UserDTO 对象
     *
     * @param user 用户
     * @return UserDTO
     */
    private UserDTO convertToUserDTO(User user) {
        if (user == null) {
            return null;
        }
        // 格式化房间信息
        String formattedRoomDisplay = RoomFormatUtil.formatRoomDisplay(user.getFloorId(), user.getRoomId());
        // 创建 UserDTO 对象
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRoomDisplay(formattedRoomDisplay);
        // 返回 UserDTO 对象
        return userDTO;
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户
     */
    @Override
    public UserDTO getUserProfile(Long userId) {
        // 通过用户ID查询用户
        User user = userMapper.findByUserId(userId);
        // 转换为 UserDTO 对象
        return convertToUserDTO(user);
    }

    /**
     * 通过用户名查询用户ID
     *
     * @param username 用户名
     * @return 用户ID
     */
    @Override
    public Long getUserIdByUsername(String username) {
        // 查询数据库，返回 User 对象
        User user = userMapper.findByUsername(username);
        // 从 User 对象中获取 ID
        return user != null ? user.getId() : null;
    }

    /**
     * 获取所有用户
     *
     * @return 所有用户
     */
    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userMapper.getAllUser();

        // 转换为 UserDTO 列表
        return users.stream().map(user -> {
            // 创建 UserDTO 对象
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            // 格式化房间信息
            userDTO.setRoomDisplay(RoomFormatUtil.formatRoomDisplay(user.getFloorId(), user.getRoomId()));
            // 返回 UserDTO 对象
            return userDTO;
            // 转换为 List
        }).collect(Collectors.toList());
    }
}
