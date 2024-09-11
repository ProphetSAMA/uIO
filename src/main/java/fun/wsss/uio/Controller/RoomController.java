package fun.wsss.uio.Controller;

import fun.wsss.uio.Entity.room.Building;
import fun.wsss.uio.Entity.room.Floor;
import fun.wsss.uio.Entity.room.Room;
import fun.wsss.uio.Service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private IRoomService roomService;
    @GetMapping("/rooms")
    public Map getRooms() {
//        List<Building> buildings = new ArrayList<>();

        List<Room> roomList =new ArrayList<>();
        Map map=new HashMap();
        Map map1=new HashMap();
       List<Room> rooms= roomService.getRooms(0L);
       List<Room> rooms1=new ArrayList<>();
        Room room=new Room();
        for (int i=0;i<rooms.size();i++){
            roomList.addAll(roomService.getRooms(rooms.get(i).getRoomsId()));
            rooms.get(i).setRooms(roomList);
        }
        map.put("rooms",rooms);


        return map;

//        // 生成33栋楼，每栋楼5个楼层，每个楼层22个房间
//        for (int buildingNum = 1; buildingNum <= 2; buildingNum++) {
//            Building building = new Building();
//            building.setBuildingName(buildingNum + "号楼");
//
//            List<Floor> floors = new ArrayList<>();
//            for (int floorNum = 1; floorNum <= 3; floorNum++) {
//                Floor floor = new Floor();
//                floor.setFloorName(floorNum + "层");
//
//                List<Room> rooms = new ArrayList<>();
//                for (int roomNum = 1; roomNum <= 5; roomNum++) {
//                    rooms.add(createRoom(buildingNum, floorNum, roomNum));
//                }
//                floor.setRooms(rooms);
//                floors.add(floor);
//            }
//            building.setFloors(floors);
//            buildings.add(building);
//        }


    }

//    private Room createRoom(int building, int floor, int room) {
//        Room newRoom = new Room();
//        newRoom.setRoom(Integer.parseInt(floor + String.format("%02d", room)));
//        newRoom.setRoomsid((long) (building * 1000L + floor * 100L + room));
//        return newRoom;
//    }
}