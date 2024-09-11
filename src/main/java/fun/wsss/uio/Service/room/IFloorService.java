package fun.wsss.uio.Service.room;

import fun.wsss.uio.Entity.room.Floor;
import java.util.List;

/**
 * 楼层Service接口
 * @author Wsssfun
 */
public interface IFloorService {
    List<Floor> getFloorsByBuildingId(Long buildingId);
}
