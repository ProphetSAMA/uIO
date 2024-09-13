package fun.wsss.uio.service.user;

import fun.wsss.uio.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Service接口
 *
 * @author Wsssfun
 */
@Service
public interface UserService {
    /**
     * 用户注册
     *
     * @param username     用户名
     * @param password     密码
     * @param selectedRoom 选中的房间
     */
    void register(String username, String password, List<Integer> selectedRoom);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    String login(String username, String password);

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户
     */
    User getUserProfile(Long userId);
}

