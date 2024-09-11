package fun.wsss.uio.Controller.room;

import fun.wsss.uio.Entity.room.Room;
import fun.wsss.uio.Service.room.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 房间Controller
 * @author Wsssfun
 */
@RestController
@RequestMapping("/api")
public class RoomController {
    @Autowired
    private IRoomService roomService;

    @GetMapping("/rooms")
    public List<Room> getRoomsByFloorId(@RequestParam Long floorId) {
        return roomService.getRoomsByFloorId(floorId);
    }
}