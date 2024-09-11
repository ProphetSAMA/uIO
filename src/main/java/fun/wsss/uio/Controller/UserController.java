package fun.wsss.uio.Controller;

import fun.wsss.uio.Entity.User;
import fun.wsss.uio.Entity.room.Room;
import fun.wsss.uio.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @PostMapping("/register")
//    public User Register(@RequestBody User user, @RequestBody Room room) {
//        if (user.getUsername() == null) {
//            throw new RuntimeException("用户名不能为空");
//        }
//        return userService.save(user,room);
//    }
//
//    @GetMapping("/login")
//    public User Login(@RequestBody User user) {
//        User existingUser = userService.findByUsername(user.getUsername());
//        if (existingUser != null && bCryptPasswordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
//            return existingUser;
//        }
//        throw new RuntimeException("用户名或密码错误");
//    }
}
