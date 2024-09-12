package fun.wsss.uio.service.room;

import fun.wsss.uio.model.room.Building;

import java.util.List;

/**
 * 楼栋Service接口
 * @author Wsssfun
 */
public interface BuildingService {
    /**
     * 获取所有楼栋
     * @return 楼栋列表
     */
    List<Building> getAllBuildings();
}

