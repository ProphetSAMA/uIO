package fun.wsss.uio.utils;

import fun.wsss.uio.mapper.PowerMapper;
import fun.wsss.uio.model.Power;
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


    public ScheduledTasks(PowerService powerService) {
        this.powerService = powerService;
        this.startTime = LocalDateTime.now();
    }

    /**
     * 定时插入数据，每小时执行一次
     */
    @Scheduled(cron = "0 0 * * * ?") // 每小时执行一次
    public void insertLastedPowerValue() {
        powerService.insertPowerValue();
        logger.info("定时任务执行了");
    }

    /**
     * 刷新Redis缓存中的数据
     *
     * @return 刷新的数据
     */
    @CachePut(value = "allPowerValue")
    @Scheduled(cron = "0 0 * * * ?") // 每小时执行一次 延后3秒执行
    public List<Power> refreshAllPowerValue() {
        // 检查是否已经过了初始延迟时间
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