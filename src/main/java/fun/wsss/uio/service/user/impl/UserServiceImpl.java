package fun.wsss.uio.service.user.impl;

import fun.wsss.uio.mapper.user.UserMapper;
import fun.wsss.uio.model.user.User;
import fun.wsss.uio.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 用户Service实现类
 * @author Wsssfun
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(String username, String password, List<Integer> selectedRoom) {
        // 确保 selectedRoom 至少有三个值
        if (selectedRoom == null || selectedRoom.size() < 3) {
            throw new IllegalArgumentException("房间没有被正确选择");
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
}
