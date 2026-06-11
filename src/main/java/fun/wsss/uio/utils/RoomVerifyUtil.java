package fun.wsss.uio.utils;

import fun.wsss.uio.model.room.Building;
import fun.wsss.uio.model.room.Floor;
import fun.wsss.uio.model.room.Room;

/**
 * 房间验证标识工具类
 *
 * @author Wsssfun
 */
public class RoomVerifyUtil {

    /**
     * 生成房间验证标识
     * 格式：mainArea-subArea--floor-buildingNumber + floor + roomNumber
     * 例如：2-1--3-7301
     *
     * @param building 楼栋信息
     * @param floor    楼层信息
     * @param room     房间信息
     * @return 房间验证标识
     */
    public static String generateRoomVerify(Building building, Floor floor, Room room) {
        if (building == null || floor == null || room == null) {
            throw new IllegalArgumentException("楼栋、楼层或房间信息不能为空");
        }
        if (building.getMainArea() == null || building.getSubArea() == null) {
            throw new IllegalArgumentException("楼栋缺少区域信息");
        }
        if (room.getRoomNumber() == null || room.getRoomNumber().isEmpty()) {
            throw new IllegalArgumentException("房间缺少房间号");
        }

        // 从楼层名称提取楼层数字，如 "3层" -> "3"
        String floorStr = floor.getName().replace("层", "");

        // 构建 roomVerify: mainArea-subArea--floor-buildingNumber + floor + roomNumber
        // 例如: 2-1--3-7301
        return building.getMainArea() + "-" + building.getSubArea() + "--" + floorStr + "-" + building.getId() + floorStr + room.getRoomNumber();
    }
}
