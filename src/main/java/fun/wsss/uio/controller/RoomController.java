package fun.wsss.uio.controller;

import fun.wsss.uio.model.room.Building;
import fun.wsss.uio.model.room.Floor;
import fun.wsss.uio.model.room.Room;
import fun.wsss.uio.service.room.BuildingService;
import fun.wsss.uio.service.room.FloorService;
import fun.wsss.uio.service.room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 房间Controller
 *
 * @author Wsssfun
 */
@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private FloorService floorService;

    @Autowired
    private RoomService roomService;

    /**
     * 获取房间列表
     *
     * @return 房间列表
     */
    @GetMapping
    public List<Map<String, Object>> getRooms() {
        List<Building> buildings = buildingService.getAllBuildings();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Building building : buildings) {
            Map<String, Object> buildingMap = new HashMap<>();
            buildingMap.put("value", building.getId());
            buildingMap.put("label", building.getName());

            List<Floor> floors = floorService.getFloorsByBuildingId(building.getId());
            List<Map<String, Object>> floorList = new ArrayList<>();

            for (Floor floor : floors) {
                Map<String, Object> floorMap = new HashMap<>();
                floorMap.put("value", floor.getId());
                floorMap.put("label", floor.getName());

                List<Room> rooms = roomService.getRoomsByFloorId(floor.getId());
                List<Map<String, Object>> roomList = new ArrayList<>();

                for (Room room : rooms) {
                    Map<String, Object> roomMap = new HashMap<>();
                    roomMap.put("value", room.getId());
                    roomMap.put("label", room.getName());
                    roomList.add(roomMap);
                }

                floorMap.put("children", roomList);
                floorList.add(floorMap);
            }

            buildingMap.put("children", floorList);
            result.add(buildingMap);
        }
        return result;
    }
}

