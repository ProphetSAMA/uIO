package fun.wsss.uio.utils;

import fun.wsss.uio.mapper.room.BuildingMapper;
import fun.wsss.uio.mapper.room.RoomMapper;
import fun.wsss.uio.mapper.user.UserMapper;
import fun.wsss.uio.mapper.PowerMapper;
import fun.wsss.uio.model.Power;
import fun.wsss.uio.model.room.Building;
import fun.wsss.uio.model.room.Room;
import fun.wsss.uio.model.user.User;
import fun.wsss.uio.service.power.PowerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类
 *
 * @author Wsssfun
 */
@Component
public class ScheduledTasks {
    private static final Logger logger = LogManager.getLogger(ScheduledTasks.class);

    private final PowerService powerService;
    private final LocalDateTime startTime;

    @Autowired
    private PowerMapper powerMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private BuildingMapper buildingMapper;

    public ScheduledTasks(PowerService powerService) {
        this.powerService = powerService;
        this.startTime = LocalDateTime.now();
    }

    /**
     * 定时插入数据，每小时执行一次
     * 遍历所有用户，为每个用户的房间插入电量数据
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void insertLastedPowerValue() {
        List<User> users = userMapper.getAllUser();
        for (User user : users) {
            Room room = roomMapper.selectById(user.getRoomId());
            Building building = buildingMapper.selectById(user.getBuildingId());
            if (room != null && building != null) {
                try {
                    String roomVerify = RoomVerifyUtil.generateRoomVerify(building, room);
                    powerService.insertPowerValue(roomVerify);
                    logger.info("定时任务执行成功 - 用户: {}, 房间: {}", user.getUsername(), roomVerify);
                } catch (Exception e) {
                    logger.error("定时任务执行失败 - 用户: {}, 错误: {}", user.getUsername(), e.getMessage(), e);
                }
            }
        }
    }

    /**
     * 刷新Redis缓存中的数据
     *
     * @return 刷新的数据
     */
    @CachePut(value = "allPowerValue")
    @Scheduled(cron = "0 0 * * * ?")
    public List<Power> refreshAllPowerValue() {
        int start = 3;
        if (Duration.between(startTime, LocalDateTime.now()).getSeconds() < start) {
            logger.info("初始延迟时间未到，跳过执行");
            return null;
        }

        logger.info("refreshAllPowerValue 开始执行，刷新Redis缓存");
        List<Power> powerList = powerMapper.selectList(null);
        logger.info("refreshAllPowerValue 执行结束，缓存已更新");
        return powerList;
    }
}
