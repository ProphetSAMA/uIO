package fun.wsss.uio.utils;

import fun.wsss.uio.mapper.PowerMapper;
import fun.wsss.uio.model.Power;
import fun.wsss.uio.service.PowerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Autowired
    private PowerMapper powerMapper;


    public ScheduledTasks(PowerService powerService) {
        this.powerService = powerService;
    }

    /**
     * 定时插入数据，每小时执行一次
     */
    @Scheduled(fixedRate = 3600000) // 每小时执行一次，单位为毫秒
    public void reportCurrentTime() {
        // 这里放要定时执行的方法
        powerService.insertPowerValue();
        logger.info("定时任务执行了");
    }

    /**
     * 刷新Redis缓存中的数据
     *
     * @return 刷新的数据
     */
    @CachePut(value = "allPowerValue")
    @Scheduled(fixedRate = 3600000) // 每小时执行一次，单位为毫秒
    public List<Power> refreshAllPowerValue() {
        logger.info("Scheduled Task: refreshAllPowerValue 开始执行，刷新Redis缓存");
        List<Power> powerList = powerMapper.selectList(null);
        logger.info("Scheduled Task: refreshAllPowerValue 执行结束，缓存已更新");
        return powerList;
    }
}