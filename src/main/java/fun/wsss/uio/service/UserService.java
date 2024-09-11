package fun.wsss.uio.service;

import fun.wsss.uio.model.User;

public interface UserService {
    User registerUser(User user);
    User loginUser(String username, String password);
}

