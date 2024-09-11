package fun.wsss.uio.Controller.room;

import fun.wsss.uio.Entity.room.Building;
import fun.wsss.uio.Service.room.IBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 楼号Controller
 * @author Wsssfun
 */
@RestController
@RequestMapping("/api")
public class BuildingController {
    @Autowired
    private IBuildingService buildingService;

    @GetMapping("/buildings")
    public List<Building> getBuildings() {
        return buildingService.getAllBuildings();
    }
}