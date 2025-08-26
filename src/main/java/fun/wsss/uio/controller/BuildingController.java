package fun.wsss.uio.controller;

import fun.wsss.uio.model.Building;
import fun.wsss.uio.service.BuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buildings")
@RequiredArgsConstructor
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping
    public List<Building> getAllBuildings() {
        return buildingService.getAllBuildings();
    }

    @GetMapping("/area/{areaId}")
    public List<Building> getBuildingsByArea(@PathVariable Integer areaId) {
        return buildingService.getBuildingsByAreaId(areaId);
    }

    @GetMapping("/{id}")
    public Building getBuildingById(@PathVariable Integer id) {
        return buildingService.getBuildingById(id);
    }
} 