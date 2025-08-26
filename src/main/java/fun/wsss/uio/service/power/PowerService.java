package fun.wsss.uio.service.power;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.wsss.uio.model.Power;
import fun.wsss.uio.model.RoomConfig;

import java.util.List;

/**
 * 电量服务接口
 *
 * @author Wsssfun
 */
public interface PowerService extends IService<Power> {
    
    /**
     * 查询并保存电量
     *
     * @param room 房间配置
     */
    void queryAndSavePower(RoomConfig room);
    
    /**
     * 查询并保存电量
     *
     * @param mainArea 主区域
     * @param subArea 子区域
     * @param floor 楼层
     * @param roomNumber 房间号
     */
    void queryAndSavePower(int mainArea, int subArea, int floor, int roomNumber);
    
    /**
     * 定时查询电量任务
     */
    void scheduledQueryPower();
    
    /**
     * 获取最新电量值
     *
     * @param mainArea 主区域
     * @param subArea 子区域
     * @param floor 楼层
     * @param roomNumber 房间号
     * @return 电量值
     */
    Power getLatestPowerValue(int mainArea, int subArea, int floor, int roomNumber);
    
    /**
     * 获取所有电量记录
     *
     * @return 电量记录列表
     */
    List<Power> selectAllPowerValue();
    
    /**
     * 获取最近一周的电量记录
     *
     * @return 电量记录列表
     */
    List<Power> selectRecentWeekPowerValue();
}
