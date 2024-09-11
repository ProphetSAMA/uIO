package fun.wsss.uio.service.room.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import fun.wsss.uio.mapper.DormitoryMapper;
import fun.wsss.uio.service.DormitoryService;
import fun.wsss.uio.model.Dormitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DormitoryServiceImpl implements DormitoryService {
    @Autowired
    private DormitoryMapper dormitoryMapper;

    @Override
    public List<Dormitory> getAllDormitories() {
        return dormitoryMapper.selectList(new QueryWrapper<>());
    }
}
