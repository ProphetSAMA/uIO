package fun.wsss.uio.controller;

import fun.wsss.uio.dto.user.UserDTO;

import fun.wsss.uio.model.user.User;
import fun.wsss.uio.service.user.UserService;
import fun.wsss.uio.utils.RoomFormatUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        @SuppressWarnings("unchecked")
        List<Integer> selectedRoom = (List<Integer>) requestBody.get("selectedRoom");

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
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestBody) {
        String username = requestBody.get("username");
        String password = requestBody.get("password");

        String token = userService.login(username, password);

        if (token != null) {
            logger.info("用户 {} 登录成功", username);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("用户名或密码错误");
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
        User user = userService.getUserProfile(userId);

        // 使用自定义进制格式化楼层和房间信息
        String formattedRoomDisplay = RoomFormatUtil.formatRoomDisplay(user.getFloorId(), user.getRoomId());

        // 返回 DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setRoomDisplay(formattedRoomDisplay);

        return userDTO;
    }


}
