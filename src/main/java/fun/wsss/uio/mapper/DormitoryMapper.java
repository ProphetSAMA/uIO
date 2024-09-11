package fun.wsss.uio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import fun.wsss.uio.model.Dormitory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 宿舍Mapper接口
 * @author Wsssfun
 */
@Mapper
public interface DormitoryMapper extends BaseMapper<Dormitory> {
    // Custom queries can be added here
}
