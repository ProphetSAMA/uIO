package fun.wsss.uio.Service.room;

import fun.wsss.uio.Entity.room.Building;
import java.util.List;

/**
 * 楼号Service接口
 * @author Wsssfun
 */
public interface IBuildingService {
    List<Building> getAllBuildings();
}
