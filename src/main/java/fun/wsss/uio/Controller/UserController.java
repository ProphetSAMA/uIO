package fun.wsss.uio.Controller;

import fun.wsss.uio.DTO.RegistrationRequest;
import fun.wsss.uio.Entity.User;
import fun.wsss.uio.Service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 * @author Wsssfun
 */
@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/register")
    public User register(@RequestBody RegistrationRequest request) {
        User user = request.getUser();
        if (user.getUsername() == null) {
            throw new RuntimeException("用户名不能为空");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userService.save(user, request.getRoom());
    }

    @GetMapping("/login")
    public User login(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            return existingUser;
        }
        throw new RuntimeException("用户名或密码错误");
    }
}