package fun.wsss.uio.service;

import fun.wsss.uio.model.PowerRecord;
import java.time.LocalDateTime;
import java.util.List;

public interface PowerRecordService {
    // 查询最新电量
    PowerRecord getLatestPowerRecord(Integer roomId);
    
    // 查询指定时间范围的电量记录
    List<PowerRecord> getPowerRecords(Integer roomId, LocalDateTime startTime, LocalDateTime endTime);
    
    // 查询所有房间在指定时间范围的电量记录
    List<PowerRecord> getAllPowerRecords(LocalDateTime startTime, LocalDateTime endTime);
    
    // 更新指定房间的电量记录
    void updateRoomPower(Integer roomId);
    
    // 更新所有房间的电量记录
    void updateAllRoomsPower();
    
    // 定时任务：每小时更新所有房间电量
    void scheduleHourlyPowerUpdate();
} 