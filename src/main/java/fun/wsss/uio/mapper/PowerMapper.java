package fun.wsss.uio.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.wsss.uio.model.Power;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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

}
