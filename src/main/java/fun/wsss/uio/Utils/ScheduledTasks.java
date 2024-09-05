package fun.wsss.uio.Utils;

import fun.wsss.uio.Service.PowerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private static final Logger logger = LogManager.getLogger(ScheduledTasks.class);

    // 注入 PowerService
    private final PowerService powerService;

    // 构造函数
    public ScheduledTasks(PowerService powerService) {
        this.powerService = powerService;
    }

    @Scheduled(fixedRate = 3600000) // 每小时执行一次，单位为毫秒
    public void reportCurrentTime() {
        // 这里放要定时执行的方法
        powerService.insertPowerValue();
        logger.info("定时任务执行了");
    }
}