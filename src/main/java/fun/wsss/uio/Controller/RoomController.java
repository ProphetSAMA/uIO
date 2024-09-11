//package fun.wsss.uio.Controller;
//
//import fun.wsss.uio.Entity.room.Building;
//import fun.wsss.uio.Entity.room.Room;
//import fun.wsss.uio.Service.room.IBuildingService;
//import fun.wsss.uio.Service.room.IRoomService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * 房间Controller
// * @author Wsssfun
// */
//@RestController
//@RequestMapping("/api")
//public class RoomController {
//    public class BuildingController {
//        @Autowired
//        private IRoomService roomService;
//
//
//        @GetMapping("/rooms")
//        public List<Room> getRoomsByFloorId(@RequestParam Long floorId) {
//            return roomService.getRoomsByFloorId(floorId);
//        }
//    }
//
//
////    @Autowired
////    private IRoomService roomService;
////
////    @GetMapping("/rooms")
////    public Map<String, List<Room>> getRooms() {
////        Map<String, List<Room>> result = new HashMap<>();
////        List<Room> rooms = roomService.getRooms(0L);
////
////        // 设置房间号
////        for (Room room : rooms) {
////            // 获取子房间
////            List<Room> childRooms = roomService.getRooms(room.getRoomsId());
////            for (Room childRoom : childRooms) {
////                // 每个房间有5个房间号
////                List<Integer> roomNumbers = new ArrayList<>();
////                for (int i = 1; i <= 5; i++) {
////                    roomNumbers.add(i);
////                }
////                childRoom.setRoomNumbers(roomNumbers);
////            }
////            room.setRooms(childRooms);
////        }
////        // 返回结果
////        result.put("rooms", rooms);
////        return result;
////    }
//}