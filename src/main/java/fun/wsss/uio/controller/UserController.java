package fun.wsss.uio.controller;

import fun.wsss.uio.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
