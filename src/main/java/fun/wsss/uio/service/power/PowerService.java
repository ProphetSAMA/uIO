package fun.wsss.uio.service.power;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public interface PowerService {

    Logger logger = Logger.getLogger(PowerService.class.getName());

    /**
     * 查询所有电量数据
     *
     * @return 电量数据列表
     */
    List<Power> selectAllPowerValue();

    /**
     * 查询最近一周电量数据
     *
     * @return 最近一周电量数据
     */
    List<Power> selectRecentWeekPowerValue();

    /**
     * 查询最新电量数据
     *
     * @return 最新电量数据
     */
    ResponseEntity<Double> getLatestPowerValue();

    /**
     * 插入最新电量数据
     */
    void insertPowerValue();

}
