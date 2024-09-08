package fun.wsss.uio.Controller;

import fun.wsss.uio.Entity.User;
import fun.wsss.uio.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User Register(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/login")
    public User Login(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            return existingUser;
        }
        throw new RuntimeException("用户名或密码错误");
    }
}
