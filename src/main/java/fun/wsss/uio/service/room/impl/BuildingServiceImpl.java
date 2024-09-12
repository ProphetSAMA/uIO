package fun.wsss.uio.service.room.impl;

import fun.wsss.uio.mapper.room.BuildingMapper;
import fun.wsss.uio.model.room.Building;
import fun.wsss.uio.service.room.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 楼栋Service实现类
 * @author Wsssfun
 */
@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingMapper buildingMapper;

    @Override
    public List<Building> getAllBuildings() {
        return buildingMapper.selectList(null);
    }
}

