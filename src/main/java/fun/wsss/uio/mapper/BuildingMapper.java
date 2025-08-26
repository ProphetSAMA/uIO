package fun.wsss.uio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.wsss.uio.model.Building;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BuildingMapper extends BaseMapper<Building> {
    
    @Select("SELECT * FROM building WHERE area_id = #{areaId}")
    List<Building> findByAreaId(Integer areaId);
} 