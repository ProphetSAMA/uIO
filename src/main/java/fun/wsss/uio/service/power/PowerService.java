package fun.wsss.uio.service.power;

import fun.wsss.uio.model.Power;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.logging.Logger;

/**
 * 电量Service
 *
 * @author Wsssfun
 */
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
     * @param roomVerify 房间验证标识
     * @return 最新电量数据
     */
    ResponseEntity<Double> getLatestPowerValue(String roomVerify);

    /**
     * 插入最新电量数据
     *
     * @param roomVerify 房间验证标识
     */
    void insertPowerValue(String roomVerify);

}
