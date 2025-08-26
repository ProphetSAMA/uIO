package fun.wsss.uio.service.impl;

import fun.wsss.uio.mapper.BuildingMapper;
import fun.wsss.uio.model.Building;
import fun.wsss.uio.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {
    private final BuildingMapper buildingMapper;

    @Override
    public List<Building> getAllBuildings() {
        return buildingMapper.selectList(null);
    }

    @Override
    public List<Building> getBuildingsByAreaId(Integer areaId) {
        return buildingMapper.findByAreaId(areaId);
    }

    @Override
    public Building getBuildingById(Integer id) {
        return buildingMapper.selectById(id);
    }
} 