package fun.wsss.uio.Service.room.impl;

import fun.wsss.uio.Entity.room.Building;
import fun.wsss.uio.Mapper.room.BuildingMapper;
import fun.wsss.uio.Service.room.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * 楼号Service实现类
 * @author Wsssfun
 */
@Service
public class BuildingServiceImpl implements IBuildingService {
    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public List<Building> getAllBuildings() {
        return buildingMapper.selectList(null);
    }
}
