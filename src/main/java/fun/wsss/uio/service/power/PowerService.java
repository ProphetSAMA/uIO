package fun.wsss.uio.service.power;

import fun.wsss.uio.model.Power;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 电量Service
 *
 * @author Wsssfun
 */
@Service
public interface PowerService {

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
     * 查询指定用户的最新电量数据
     *
     * @param userId 用户 ID
     * @return 最新电量数据
     */
    Double getLatestPowerValueByUserId(Long userId);

    /**
     * 查询指定用户最近一周的电量数据
     *
     * @param userId 用户 ID
     * @return 最近一周电量数据
     */
    List<Power> selectRecentWeekPowerValueByUserId(Long userId);

    /**
     * 查询指定用户的所有电量数据
     *
     * @param userId 用户 ID
     * @return 所有电量数据
     */
    List<Power> selectAllPowerValueByUserId(Long userId);

    /**
     * 插入最新电量数据
     */
    void insertPowerValue();

    /**
     * 插入最新电量数据（指定用户）
     *
     * @param userId 用户 ID
     */
    void insertPowerValue(Long userId);

}
