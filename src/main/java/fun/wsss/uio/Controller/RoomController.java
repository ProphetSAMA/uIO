package fun.wsss.uio.Controller;

import fun.wsss.uio.Entity.room.Building;
import fun.wsss.uio.Entity.room.Floor;
import fun.wsss.uio.Entity.room.Room;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    @GetMapping("/rooms")
    public List<Building> getRooms() {
        List<Building> buildings = new ArrayList<>();

        // 生成33栋楼，每栋楼5个楼层，每个楼层22个房间
        for (int buildingNum = 1; buildingNum <= 33; buildingNum++) {
            Building building = new Building();
            building.setBuildingName(buildingNum + "号楼");

            List<Floor> floors = new ArrayList<>();
            for (int floorNum = 1; floorNum <= 5; floorNum++) {
                Floor floor = new Floor();
                floor.setFloorName(floorNum + "层");

                List<Room> rooms = new ArrayList<>();
                for (int roomNum = 1; roomNum <= 22; roomNum++) {
                    rooms.add(createRoom(buildingNum + "号楼", floorNum, roomNum));
                }
                floor.setRooms(rooms);
                floors.add(floor);
            }
            building.setFloors(floors);
            buildings.add(building);
        }

        return buildings;
    }

    private Room createRoom(String building, int floor, int room) {
        Room newRoom = new Room();
        newRoom.setBuilding(building);
        newRoom.setFloor(floor + "层");
        newRoom.setRoom(floor + String.format("%02d", room));
        return newRoom;
    }
}