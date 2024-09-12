package fun.wsss.uio.service.user;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户Service接口
 * @author Wsssfun
 */
@Service
public interface UserService {
    /**
     * 用户注册
     * @param username 用户名
     * @param password 密码
     * @param selectedRoom 选中的房间
     */
    void register(String username, String password, List<Integer> selectedRoom);
}

