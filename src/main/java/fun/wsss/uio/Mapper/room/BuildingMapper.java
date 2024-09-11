package fun.wsss.uio.Mapper.room;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.wsss.uio.Entity.room.Building;
import org.apache.ibatis.annotations.Mapper;

/**
 * 楼号Mapper接口
 * @author Wsssfun
 */
@Mapper
public interface BuildingMapper extends BaseMapper<Building> {
}