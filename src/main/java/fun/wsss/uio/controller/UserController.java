package fun.wsss.uio.controller;

import fun.wsss.uio.dto.user.UserDTO;
import fun.wsss.uio.mapper.user.UserMapper;
import fun.wsss.uio.service.user.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 *
 * @author Wsssfun
 */
@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param requestBody 请求体
     * @return 注册结果
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, Object> requestBody) {
        String username = (String) requestBody.get("username");
        String password = (String) requestBody.get("password");
        @SuppressWarnings("unchecked") List<Integer> selectedRoom = (List<Integer>) requestBody.get("selectedRoom");

        if (userMapper.findByUsername(username) != null) {
            return ResponseEntity.status(400).body("用户名已存在");
        }
        userService.register(username, password, selectedRoom);

        return ResponseEntity.ok("用户注册成功");
    }

    /**
     * 用户登录
     *
     * @param requestBody 请求体
     * @return 登录结果
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        // 调用 userService.login 返回 token
        String token = userService.login(username, password);

        // 调用 userService 通过用户名获取用户ID
        Long userId = userService.getUserIdByUsername(username);

        if (token != null) {
            logger.info("用户 {} 登录成功", username);

            // 创建响应对象，包含 token, userId 和 username
            Map<String, Object> response = new HashMap<>(3);
            response.put("token", token);
            response.put("userId", userId);
            response.put("username", username);

            // 返回响应
            return ResponseEntity.ok(response);
        } else {
            // 返回 401 错误，用户名或密码错误
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/profile")
    public UserDTO getUserProfile(@RequestParam("userId") Long userId) {
        UserDTO userDTO = userService.getUserProfile(userId);
        // 如果用户不存在，抛出异常
        if (userDTO == null) {
            throw new RuntimeException("用户不存在");
        }
        return userService.getUserProfile(userId);
    }

    @GetMapping("/all")
    public List<UserDTO> getAllUser() {
        return userService.getAllUser();
    }
}



