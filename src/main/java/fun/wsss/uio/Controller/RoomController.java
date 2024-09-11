package fun.wsss.uio.Controller;

import fun.wsss.uio.model.Room;
import fun.wsss.uio.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Wsssfun
 * @Data localhost:8080/rooms?buildingId=2&floorId=3
 */


@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getRooms(@RequestParam(value = "buildingId", required = false) Integer buildingId, @RequestParam(value = "floorId", required = false) Integer floorId) {

        if (buildingId == null || floorId == null) {
            // 处理参数缺失的情况
            return ResponseEntity.badRequest().body(null);
        }
        List<Room> rooms = roomService.getRoomsByBuildingAndFloor(buildingId, floorId);

        return ResponseEntity.ok(rooms);
    }

}


