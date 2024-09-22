package fun.wsss.uio.utils;

/**
 * 房间格式化工具类
 *
 * @author Wsssfun
 * @date 2024年9月13日 23点37分
 */
public class RoomFormatUtil {

    /**
     * 格式化楼层
     *
     * @param floorNumber 层号
     *
     * @return 格式化后的楼层
     */
    public static String formatFloor(int floorNumber) {
        int buildingNumber = (floorNumber - 1) / 5 + 1;
        int relativeFloor = (floorNumber - 1) % 5 + 1;
        return buildingNumber + "#" + relativeFloor;
    }

    /**
     * 格式化房间
     *
     * @param roomNumber 房号
     *
     * @return 格式化后的房间
     */
    public static String formatRoom(int roomNumber) {
        int relativeRoom = (roomNumber - 1) % 22 + 1;
        return String.format("%02d", relativeRoom);
    }

    /**
     * 格式化房间显示
     *
     * @param floorNumber 层号
     * @param roomNumber  房号
     *
     * @return 格式化后的房间显示
     */
    public static String formatRoomDisplay(int floorNumber, int roomNumber) {
        String floorDisplay = formatFloor(floorNumber);
        String roomDisplay = formatRoom(roomNumber);
        return floorDisplay + roomDisplay;
    }
}
