package fun.wsss.uio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.wsss.uio.model.Power;
import fun.wsss.uio.model.RoomConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
     * 获取房间最新电量记录
     */
    @Select("SELECT * FROM power WHERE room_number = #{roomNumber} " +
            "AND main_area = #{mainArea} AND sub_area = #{subArea} " +
            "ORDER BY querytime DESC LIMIT 1")
    Power getLatestPowerByRoom(@Param("mainArea") Integer mainArea,
                              @Param("subArea") Integer subArea,
                              @Param("roomNumber") Integer roomNumber);

    @Select("SELECT building_id, floor_id, room_id FROM room_info")
    List<RoomConfig> getAllRoomConfigs();

    /**
     * 查询所有房间配置
     *
     * @return 房间配置列表
     */
    @Select("SELECT DISTINCT main_area as buildingId, sub_area as floorId, floor as floorId, room_number as roomId FROM power")
    List<RoomConfig> selectAllRoomConfigs();

}
