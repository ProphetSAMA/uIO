package fun.wsss.uio.service.power.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.wsss.uio.mapper.PowerMapper;
import fun.wsss.uio.model.Power;
import fun.wsss.uio.model.PowerQueryResult;
import fun.wsss.uio.model.RoomConfig;
import fun.wsss.uio.service.power.PowerService;
import fun.wsss.uio.utils.Http;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

/**
 * 电量服务实现类
 *
 * @author Wsssfun
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PowerServiceImpl extends ServiceImpl<PowerMapper, Power> implements PowerService {

    private final Http http;
    private final PowerMapper powerMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void queryAndSavePower(RoomConfig room) {
        PowerQueryResult result = http.queryPower(
            String.format("%d-%d", room.getBuildingId(), room.getFloorId()),
            room.getFloorId(),
            room.getRoomId()
        );
        
        if (!result.isSuccess()) {
            log.error("房间[{}]电量查询失败: {}", result.getRoomNumber(), result.getMessage());
            return;
        }
        
        Power lastPower = getLatestPowerValue(
            room.getBuildingId(),
            room.getFloorId(),
            room.getFloorId(),
            room.getRoomId()
        );
        
        Power power = new Power();
        power.setMainArea(room.getBuildingId());
        power.setSubArea(room.getFloorId());
        power.setFloor(room.getFloorId());
        power.setRoomNumber(room.getRoomId());
        power.setValue(result.getValue());
        power.setQuerytime(LocalDateTime.now());
        
        if (lastPower != null) {
            BigDecimal lastValue = lastPower.getValue();
            BigDecimal currentValue = result.getValue();
            double change = currentValue.subtract(lastValue).doubleValue();
            power.setChangeValue(change);
        }
        
        save(power);
        log.info("房间[{}]电量记录保存成功", result.getRoomNumber());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void queryAndSavePower(int mainArea, int subArea, int floor, int roomNumber) {
        queryAndSavePower(new RoomConfig(mainArea, subArea, floor, roomNumber));
    }

    @Override
    @Scheduled(cron = "${power.query.cron:0 0/30 * * * ?}")
    public void scheduledQueryPower() {
        log.info("开始定时查询电量任务");
        List<RoomConfig> rooms = powerMapper.selectAllRoomConfigs();
        
        for (RoomConfig room : rooms) {
            try {
                queryAndSavePower(room);
                // 避免请求过于频繁
                Thread.sleep(1000);
        } catch (Exception e) {
                log.error("房间[{}-{}-{}-{}]电量查询异常", 
                    room.getBuildingId(), room.getFloorId(), 
                    room.getFloorId(), room.getRoomId(), e);
        }
    }

        log.info("定时查询电量任务完成");
    }

    @Override
    public Power getLatestPowerValue(int mainArea, int subArea, int floor, int roomNumber) {
        LambdaQueryWrapper<Power> queryWrapper = new LambdaQueryWrapper<Power>()
            .eq(Power::getMainArea, mainArea)
            .eq(Power::getSubArea, subArea)
            .eq(Power::getFloor, floor)
            .eq(Power::getRoomNumber, roomNumber)
            .orderByDesc(Power::getQuerytime)
            .last("LIMIT 1");
        
        return getOne(queryWrapper);
    }

    @Override
    public List<Power> selectAllPowerValue() {
        LambdaQueryWrapper<Power> queryWrapper = new LambdaQueryWrapper<Power>()
            .orderByDesc(Power::getQuerytime);
        
        return list(queryWrapper);
    }

    @Override
    public List<Power> selectRecentWeekPowerValue() {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        
        LambdaQueryWrapper<Power> queryWrapper = new LambdaQueryWrapper<Power>()
            .ge(Power::getQuerytime, oneWeekAgo)
            .orderByDesc(Power::getQuerytime);
        
        return list(queryWrapper);
    }
} 