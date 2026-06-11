package fun.wsss.uio.utils;

import fun.wsss.uio.model.room.Building;
import fun.wsss.uio.model.room.Room;

/**
 * 房间验证标识工具类
 *
 * @author Wsssfun
 */
public class RoomVerifyUtil {

    /**
     * 生成房间验证标识
     * 格式：mainArea-subArea--buildingNumber + roomNumber
     * 例如：2-1--7301
     *
     * @param building 楼栋信息
     * @param room     房间信息
     * @return 房间验证标识
     */
    public static String generateRoomVerify(Building building, Room room) {
        if (building == null || room == null) {
            throw new IllegalArgumentException("楼栋或房间信息不能为空");
        }
        if (building.getMainArea() == null || building.getSubArea() == null) {
            throw new IllegalArgumentException("楼栋缺少区域信息");
        }
        if (room.getRoomNumber() == null || room.getRoomNumber().isEmpty()) {
            throw new IllegalArgumentException("房间缺少房间号");
        }

        return building.getMainArea() + "-" + building.getSubArea() + "--" + building.getId() + room.getRoomNumber();
    }
}
