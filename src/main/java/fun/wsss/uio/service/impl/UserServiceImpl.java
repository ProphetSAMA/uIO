package fun.wsss.uio.service.impl;

import fun.wsss.uio.mapper.UserMapper;
import fun.wsss.uio.model.User;
import fun.wsss.uio.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    
    @Override
    @Transactional
    public User createUser(User user) {
        userMapper.insert(user);
        return user;
    }
    
    @Override
    public User getUserById(Long id) {
        return userMapper.selectById(id);
    }
    
    @Override
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }
    
    @Override
    public User getUserByLocation(Long buildingId, Long floorId, Long roomId) {
        return userMapper.findByLocation(buildingId, floorId, roomId);
    }
    
    @Override
    @Transactional
    public void updateUser(User user) {
        userMapper.updateById(user);
    }
    
    @Override
    @Transactional
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }
} 