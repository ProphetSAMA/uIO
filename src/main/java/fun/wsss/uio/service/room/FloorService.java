package fun.wsss.uio.service.room;

import fun.wsss.uio.model.room.Floor;

import java.util.List;

/**
 * 楼层Service接口
 * @author Wsssfun
 */
public interface FloorService {
    /**
     * 根据楼栋ID获取楼层列表
     * @param buildingId 楼栋ID
     * @return 楼层列表
     */
    List<Floor> getFloorsByBuildingId(int buildingId);
}

