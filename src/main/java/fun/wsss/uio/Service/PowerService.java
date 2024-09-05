package fun.wsss.uio.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.wsss.uio.Entity.Power;
import fun.wsss.uio.Mapper.PowerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PowerService {
    Logger logger = Logger.getLogger(PowerService.class.getName());
    private final PowerMapper powerMapper;

    @Autowired
    public PowerService(PowerMapper powerMapper) {
        this.powerMapper = powerMapper;
    }

    public ResponseEntity<List<Power>> getPowerValue() {
        logger.info("getPowerValue方法开始执行");
        List<Power> powerList = powerMapper.selectList(null);
        logger.info("getPowerValue方法执行结束");
        return ResponseEntity.ok(powerList);
    }
}
