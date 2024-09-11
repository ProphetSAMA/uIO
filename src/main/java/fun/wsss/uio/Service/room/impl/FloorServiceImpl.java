package fun.wsss.uio.Service.room.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.wsss.uio.Entity.room.Floor;
import fun.wsss.uio.Mapper.room.FloorMapper;
import fun.wsss.uio.Service.room.IFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 楼层Service实现类
 * @author Wsssfun
 */
@Service
public class FloorServiceImpl implements IFloorService {
    @Autowired
    private FloorMapper floorMapper;

    @Override
    public List<Floor> getFloorsByBuildingId(Long buildingId) {
        return floorMapper.selectList(new QueryWrapper<Floor>().eq("building_id", buildingId));
    }
}
