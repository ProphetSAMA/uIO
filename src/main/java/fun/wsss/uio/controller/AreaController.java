package fun.wsss.uio.controller;

import fun.wsss.uio.model.Area;
import fun.wsss.uio.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;

    @GetMapping
    public List<Area> getAllAreas() {
        return areaService.getAllAreas();
    }

    @GetMapping("/{id}")
    public Area getAreaById(@PathVariable Integer id) {
        return areaService.getAreaById(id);
    }
} 