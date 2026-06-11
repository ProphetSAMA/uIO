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
     * 查询指定房间的所有数据
     *
     * @param roomVerify 房间验证标识
     * @return 指定房间的数据
     */
    @Override
    public List<Power> selectAllPowerValueByRoom(String roomVerify) {
        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_verify", roomVerify).ne("change_value", 0);
        return powerMapper.selectList(queryWrapper);
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

        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = oneWeekAgo.format(formatter);

        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("querytime", formattedDateTime)
                .ne("change_value", 0);

        List<Power> powerList = powerMapper.selectList(queryWrapper);

        logger.info("selectRecentWeekPowerValue 方法执行结束");
        return powerList;
    }

    /**
     * 查询指定房间近一周内 change_value 非零的数据
     *
     * @param roomVerify 房间验证标识
     * @return 指定房间近一周的数据
     */
    @Override
    public List<Power> selectRecentWeekPowerValueByRoom(String roomVerify) {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = oneWeekAgo.format(formatter);

        QueryWrapper<Power> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_verify", roomVerify)
                .ge("querytime", formattedDateTime)
                .ne("change_value", 0);

        return powerMapper.selectList(queryWrapper);
    }


    /**
     * 查询最新数据
     *
     * @param roomVerify 房间验证标识
     * @return 最新数据
     */
    @Override
    public ResponseEntity<Double> getLatestPowerValue(String roomVerify) {
        logger.info("getLatestPowerValue方法开始执行");

        Http http = new Http();
        String response = http.sendPostRequest(roomVerify);
        Json json = new Json();
        json.processResponse(response);
        Double quantityStr = json.quantity;
        logger.info("getLatestPowerValue方法执行结束");
        return ResponseEntity.ok(quantityStr);
    }

    /**
     * 插入最新电量数据
     *
     * @param roomVerify 房间验证标识
     */
    @Override
    public void insertPowerValue(String roomVerify) {
        logger.info("insertPowerValue方法开始执行");

        Http http = new Http();
        String response = http.sendPostRequest(roomVerify);
        Json json = new Json();
        json.processResponse(response);
        Double quantityStr = json.quantity;
        logger.info("最新数据: " + quantityStr);

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);

        Power lastPower = powerMapper.selectLastRecordByRoom(roomVerify);
        BigDecimal changeValue = BigDecimal.ZERO;

        if (lastPower != null) {
            BigDecimal lastValue = BigDecimal.valueOf(lastPower.getValue());
            BigDecimal currentValue = BigDecimal.valueOf(quantityStr);
            changeValue = currentValue.subtract(lastValue);
            logger.info("值变化: " + changeValue);
        } else {
            logger.info("无法获取最后一条记录");
        }

        Power power = new Power(quantityStr, formattedDateTime);
        power.setChangeValue(changeValue.doubleValue());
        power.setRoomVerify(roomVerify);
        logger.info("Power 插入的值 - value: " + power.getValue()
                + ", querytime: " + power.getQuerytime()
                + ", changeValue: " + power.getChangeValue());

        powerMapper.insert(power);

        logger.info("insertPowerValue方法执行结束");
    }
}
