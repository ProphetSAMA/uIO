package fun.wsss.uio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.wsss.uio.model.PowerRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PowerRecordMapper extends BaseMapper<PowerRecord> {
    
    @Select("SELECT * FROM power_record WHERE room_id = #{roomId} " +
            "ORDER BY record_time DESC LIMIT 1")
    PowerRecord findLatestByRoomId(Integer roomId);
    
    @Select("SELECT * FROM power_record WHERE room_id = #{roomId} " +
            "AND record_time BETWEEN #{startTime} AND #{endTime} " +
            "ORDER BY record_time DESC")
    List<PowerRecord> findByRoomIdAndTimeRange(
            @Param("roomId") Integer roomId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
    
    @Select("SELECT * FROM power_record WHERE " +
            "record_time BETWEEN #{startTime} AND #{endTime} " +
            "ORDER BY room_id, record_time DESC")
    List<PowerRecord> findAllByTimeRange(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
} 