package fun.wsss.uio.service;

import fun.wsss.uio.model.Building;
import java.util.List;

public interface BuildingService {
    List<Building> getAllBuildings();
    List<Building> getBuildingsByAreaId(Integer areaId);
    Building getBuildingById(Integer id);
} 