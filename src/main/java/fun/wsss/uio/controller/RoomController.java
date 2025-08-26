package fun.wsss.uio.controller;

import fun.wsss.uio.model.Room;
import fun.wsss.uio.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房间Controller
 *
 * @author Wsssfun
 */
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    @GetMapping("/building/{buildingId}")
    public List<Room> getRoomsByBuilding(@PathVariable Integer buildingId) {
        return roomService.getRoomsByBuildingId(buildingId);
    }

    @GetMapping("/building/{buildingId}/floor/{floor}")
    public List<Room> getRoomsByBuildingAndFloor(
            @PathVariable Integer buildingId,
            @PathVariable Integer floor) {
        return roomService.getRoomsByBuildingAndFloor(buildingId, floor);
    }

    @GetMapping("/{id}")
    public Room getRoomById(@PathVariable Integer id) {
        return roomService.getRoomById(id);
    }
}

