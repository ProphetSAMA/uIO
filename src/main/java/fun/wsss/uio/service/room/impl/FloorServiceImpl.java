package fun.wsss.uio.service.room.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import fun.wsss.uio.mapper.room.FloorMapper;
import fun.wsss.uio.model.room.Floor;
import fun.wsss.uio.service.room.FloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 楼层Service实现类
 *
 * @author Wsssfun
 */
@Service
public class FloorServiceImpl implements FloorService {

    @Autowired
    private FloorMapper floorMapper;

    /**
     * 根据楼栋ID获取楼层列表
     *
     * @param buildingId 楼栋ID
     * @return 楼层列表
     */
    @Cacheable(value = "floorCache", key = "#buildingId")
    @Override
    public List<Floor> getFloorsByBuildingId(int buildingId) {
        QueryWrapper<Floor> wrapper = new QueryWrapper<>();
        wrapper.eq("building_id", buildingId);
        return floorMapper.selectList(wrapper);
    }
}

