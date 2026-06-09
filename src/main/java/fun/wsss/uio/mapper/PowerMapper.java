package fun.wsss.uio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.wsss.uio.model.Power;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 电量Mapper接口
 *
 * @author Wsssfun
 */
@Mapper
public interface PowerMapper extends BaseMapper<Power> {

    /**
     * 查询最后一条记录
     *
     * @return 最后一条记录
     */
    @Select("SELECT * FROM power ORDER BY queryTime DESC LIMIT 1")
    Power selectLastRecord();

    /**
     * 查询指定用户的最新电量数据
     *
     * @param userId 用户 ID
     * @return 最新电量数据
     */
    @Select("SELECT * FROM power WHERE user_id = #{userId} ORDER BY queryTime DESC LIMIT 1")
    Power selectLatestByUserId(Long userId);

    /**
     * 查询指定用户最近一周的电量数据（非零变化值）
     *
     * @param userId 用户 ID
     * @param days   天数
     * @return 最近 N 天的电量数据列表
     */
    @Select("SELECT * FROM power WHERE user_id = #{userId} AND change_value != 0 AND queryTime >= DATE_SUB(NOW(), INTERVAL #{days} DAY) ORDER BY queryTime DESC")
    List<Power> selectRecentPowerByUserIdAndDays(Long userId, int days);

    /**
     * 查询指定用户的所有电量数据（非零变化值）
     *
     * @param userId 用户 ID
     * @return 所有电量数据列表
     */
    @Select("SELECT * FROM power WHERE user_id = #{userId} AND change_value != 0 ORDER BY queryTime DESC")
    List<Power> selectAllByUserId(Long userId);
}
