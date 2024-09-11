package fun.wsss.uio.Controller.room;

import fun.wsss.uio.Entity.room.Floor;
import fun.wsss.uio.Service.room.IFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 楼层Controller
 * @author Wsssfun
 */
@RestController
@RequestMapping("/api")
public class FloorController {
    @Autowired
    private IFloorService floorService;

    @GetMapping("/floors")
    public List<Floor> getFloorsByBuildingId(@RequestParam Long buildingId) {
        return floorService.getFloorsByBuildingId(buildingId);
    }
}