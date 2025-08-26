package fun.wsss.uio.service;

import fun.wsss.uio.model.User;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    User getUserByLocation(Long buildingId, Long floorId, Long roomId);
    void updateUser(User user);
    void deleteUser(Long id);
} 