package fun.wsss.uio.service;

import fun.wsss.uio.mapper.PowerMapper;
import fun.wsss.uio.model.Power;
import fun.wsss.uio.utils.Http;
import fun.wsss.uio.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

/**
 * 电量Service
 *
 * @author Wsssfun
 */
@Service
public class PowerService {
    private final PowerMapper powerMapper;
    Logger logger = Logger.getLogger(PowerService.class.getName());

    @Autowired
    public PowerService(PowerMapper powerMapper) {
        this.powerMapper = powerMapper;
    }

    /**
     * 查询所有数据
     *
     * @return 所有数据
     */
    @Cacheable(value = "allPowerValue")
    public List<Power> selectAllPowerValue() {
        logger.info("getPowerValue方法开始执行");
        List<Power> powerList = powerMapper.selectList(null);
        logger.info("getPowerValue方法执行结束");
        return powerList;
    }

    /**
     * 查询最新数据
     *
     * @return 最新数据
     */
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
     * 插入数据
     */
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
