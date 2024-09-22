package fun.wsss.uio.service.power.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.wsss.uio.mapper.PowerMapper;
import fun.wsss.uio.model.Power;
import fun.wsss.uio.service.power.PowerService;
import fun.wsss.uio.utils.Http;
import fun.wsss.uio.utils.Json;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 电量Service实现类
 *
 * @author Wsssfun
 */
@Service
public class PowerServiceImpl implements PowerService {

    private final PowerMapper powerMapper;

    public PowerServiceImpl(PowerMapper powerMapper) {
        this.powerMapper = powerMapper;
    }


    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @Override
    @Cacheable(value = "allPowerValue")
    public List<Power> selectAllPowerValue() {
        logger.info("getPowerValue方法开始执行");
        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("change_value", 0);
        List<Power> powerList = powerMapper.selectList(queryWrapper);
        logger.info("getPowerValue方法执行结束");
        return powerList;
    }

    /**
     * 查询近一周内 change_value 非零的数据
     *
     * @return 近一周非零 change_value 的数据
     */
    @Override
    @Cacheable(value = "recentWeekPowerValue")
    public List<Power> selectRecentWeekPowerValue() {
        logger.info("selectRecentWeekPowerValue 方法开始执行");

        // 获取当前时间减去7天的时间点
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);

        // 格式化日期时间为字符串，与数据库中的格式匹配
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = oneWeekAgo.format(formatter);

        // 构造查询条件：querytime >= 近7天，并且 change_value != 0
        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();
        // 近7天的数据
        queryWrapper.ge("querytime", formattedDateTime)
                // 排除 change_value 为 0 的数据
                .ne("change_value", 0);

        // 执行查询
        List<Power> powerList = powerMapper.selectList(queryWrapper);

        logger.info("selectRecentWeekPowerValue 方法执行结束");
        return powerList;
    }


    /**
     * 查询最新数据
     *
     * @return 最新数据
     */
    @Override
    public ResponseEntity<Double> getLatestPowerValue() {
        logger.info("getLatestPowerValue方法开始执行");

        Http http = new Http();
        Json json = new Json();
        json.processResponse(http);
        Double quantityStr = json.quantity;
        logger.info("getLatestPowerValue方法执行结束");
        return ResponseEntity.ok(quantityStr);
    }

    /**
     * 插入最新电量数据
     */
    @Override
    public void insertPowerValue() {
        logger.info("insertPowerValue方法开始执行");

        // 获取Json类中的数据
        Http http = new Http();
        Json json = new Json();
        json.processResponse(http);
        Double quantityStr = json.quantity;
        logger.info("最新数据: " + quantityStr);

        // 获取当前时间并格式化
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        // 查询最后一条记录，用于计算变化量
        Power lastPower = powerMapper.selectLastRecord();
        BigDecimal changeValue = BigDecimal.ZERO;

        if (lastPower != null) {
            BigDecimal lastValue = BigDecimal.valueOf(lastPower.getValue());
            BigDecimal currentValue = BigDecimal.valueOf(quantityStr);

            // 计算变化量，使用BigDecimal进行减法运算，避免精度丢失
            changeValue = currentValue.subtract(lastValue);
            logger.info("值变化: " + changeValue);
        } else {
            logger.info("无法获取最后一条记录");
        }

        // 创建Power对象并插入数据库
        Power power = new Power(quantityStr, formattedDateTime);
        // 将BigDecimal转换为double存入数据库
        power.setChangeValue(changeValue.doubleValue());
        logger.info("Power 插入的值 - value: " + power.getValue()
                + ", querytime: " + power.getQuerytime()
                + ", changeValue: " + power.getChangeValue());

        powerMapper.insert(power);

        logger.info("insertPowerValue方法执行结束");
    }
}
