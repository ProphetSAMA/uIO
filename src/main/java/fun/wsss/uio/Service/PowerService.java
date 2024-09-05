package fun.wsss.uio.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.wsss.uio.Entity.Power;
import fun.wsss.uio.Mapper.PowerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PowerService {
    private final PowerMapper powerMapper;

    @Autowired
    public PowerService(PowerMapper powerMapper) {
        this.powerMapper = powerMapper;
    }

    public ResponseEntity<List<Power>> getPowerValue() {
        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();
        List<Power> powerList = powerMapper.selectList(queryWrapper);
        return ResponseEntity.ok(powerList);
    }
}
