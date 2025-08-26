package fun.wsss.uio.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import fun.wsss.uio.mapper.PowerRecordMapper;
import fun.wsss.uio.mapper.RoomMapper;
import fun.wsss.uio.mapper.BuildingMapper;
import fun.wsss.uio.model.PowerRecord;
import fun.wsss.uio.model.Room;
import fun.wsss.uio.model.Building;
import fun.wsss.uio.service.PowerRecordService;
import fun.wsss.uio.utils.Http;
import fun.wsss.uio.model.PowerQueryResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CountDownLatch;

@Slf4j
@Service
@RequiredArgsConstructor
public class PowerRecordServiceImpl implements PowerRecordService {
    private final PowerRecordMapper powerRecordMapper;
    private final RoomMapper roomMapper;
    private final BuildingMapper buildingMapper;
    private final Http http;
    
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public PowerRecord getLatestPowerRecord(Integer roomId) {
        return powerRecordMapper.findLatestByRoomId(roomId);
    }

    @Override
    public List<PowerRecord> getPowerRecords(Integer roomId, LocalDateTime startTime, LocalDateTime endTime) {
        return powerRecordMapper.findByRoomIdAndTimeRange(roomId, startTime, endTime);
    }

    @Override
    public List<PowerRecord> getAllPowerRecords(LocalDateTime startTime, LocalDateTime endTime) {
        return powerRecordMapper.findAllByTimeRange(startTime, endTime);
    }

    @Override
    public void updateRoomPower(Integer roomId) {
        Room room = roomMapper.selectById(roomId);
        if (room == null) {
            log.error("房间不存在: {}", roomId);
            return;
        }

        Building building = buildingMapper.selectById(room.getBuildingId());
        if (building == null) {
            log.error("楼栋不存在: {}", room.getBuildingId());
            return;
        }

        try {
            // 获取上次电量记录
            PowerRecord lastRecord = getLatestPowerRecord(roomId);
            
            // 根据区域ID确定主区域号
            int mainArea = building.getAreaId() == 1 ? 1 : 2;
            int subArea = 1;
            
            String roomVerify = String.format("%d-%d--%d-%d", 
                mainArea, subArea, room.getFloor(), room.getFullRoomNumber());
            log.info("开始查询房间[{}]电量", roomVerify);
            
            // 查询当前电量
            PowerQueryResult result = http.queryPower(mainArea, subArea, room.getFloor(), room.getFullRoomNumber());
            
            if (result.isSuccess()) {
                BigDecimal currentValue = result.getValue();
                
                // 计算电量变化
                BigDecimal changeValue = BigDecimal.ZERO;
                if (lastRecord != null) {
                    changeValue = currentValue.subtract(lastRecord.getPowerValue());
                }
                
                // 保存新记录
                PowerRecord newRecord = new PowerRecord();
                newRecord.setRoomId(roomId);
                newRecord.setPowerValue(currentValue);
                newRecord.setRecordTime(LocalDateTime.now());
                newRecord.setChangeValue(changeValue);
                
                powerRecordMapper.insert(newRecord);
                log.info("房间[{}]电量记录保存成功: {}度", roomVerify, currentValue);
            } else {
                log.warn("房间[{}]电量查询失败: {}", roomVerify, result.getMessage());
            }
        } catch (Exception e) {
            log.warn("处理房间[{}]电量数据失败: {}", roomId, e.getMessage());
        }
    }

    @Override
    public void updateAllRoomsPower() {
        List<Room> rooms = roomMapper.selectList(null);
        int totalRooms = rooms.size();
        AtomicInteger processedCount = new AtomicInteger(0);
        
        rooms.forEach(room -> {
            executorService.submit(() -> {
                try {
                    updateRoomPower(room.getId());
                } catch (Exception e) {
                    log.warn("批量更新房间[{}]电量失败: {}", room.getId(), e.getMessage());
                } finally {
                    int current = processedCount.incrementAndGet();
                    if (current % 50 == 0 || current == totalRooms) {
                        log.info("电量更新进度: {}/{}", current, totalRooms);
                    }
                }
            });
        });
    }

    /**
     * 每小时执行一次，获取所有房间电量
     * cron = "0 0 * * * ?" 表示每小时整点执行
     */
    @Override
    @Scheduled(cron = "${power.schedule.cron:0 0 * * * ?}")
    public void scheduleHourlyPowerUpdate() {
        log.info("开始执行定时任务：更新所有房间电量 - {}", LocalDateTime.now());
        try {
            updateAllRoomsPower();
            // 等待所有任务完成或超时
            executorService.awaitTermination(30, TimeUnit.MINUTES);
            log.info("定时任务执行完成：所有房间电量已更新");
        } catch (Exception e) {
            log.error("定时任务执行异常", e);
        }
    }

    /**
     * 应用启动后延迟30秒执行一次
     */
    @Scheduled(initialDelay = 30000, fixedDelay = Long.MAX_VALUE)
    public void initializeAllRoomsPower() {
        log.info("应用启动后首次更新所有房间电量");
        scheduleHourlyPowerUpdate();
    }
} 