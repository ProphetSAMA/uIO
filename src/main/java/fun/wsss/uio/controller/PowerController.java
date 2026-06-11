package fun.wsss.uio.controller;

import fun.wsss.uio.mapper.room.BuildingMapper;
import fun.wsss.uio.mapper.room.FloorMapper;
import fun.wsss.uio.mapper.room.RoomMapper;
import fun.wsss.uio.mapper.user.UserMapper;
import fun.wsss.uio.model.Power;
import fun.wsss.uio.model.room.Building;
import fun.wsss.uio.model.room.Floor;
import fun.wsss.uio.model.room.Room;
import fun.wsss.uio.model.user.User;
import fun.wsss.uio.service.power.PowerService;
import fun.wsss.uio.utils.RoomVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 电量Controller
 *
 * @author Wsssfun
 */
@RestController
@RequestMapping("/api")
public class PowerController {

    private final PowerService powerService;
    private final UserMapper userMapper;
    private final RoomMapper roomMapper;
    private final BuildingMapper buildingMapper;
    private final FloorMapper floorMapper;

    @Autowired
    public PowerController(PowerService powerService, UserMapper userMapper, RoomMapper roomMapper, BuildingMapper buildingMapper, FloorMapper floorMapper) {
        this.powerService = powerService;
        this.userMapper = userMapper;
        this.roomMapper = roomMapper;
        this.buildingMapper = buildingMapper;
        this.floorMapper = floorMapper;
    }

    /**
     * 查询指定用户房间的所有电量数据
     *
     * @param userId 用户ID
     * @return 电量数据列表
     */
    @GetMapping("/all-power")
    public ResponseEntity<List<Power>> selectAllPowerValues(@RequestParam Long userId) {
        String roomVerify = getRoomVerifyByUserId(userId);
        return ResponseEntity.ok(powerService.selectAllPowerValueByRoom(roomVerify));
    }

    /**
     * 查询指定用户房间最近一周电量数据
     *
     * @param userId 用户ID
     * @return 最近一周电量数据
     */
    @GetMapping("/recent-week-power")
    public ResponseEntity<List<Power>> selectRecentWeekPowerValues(@RequestParam Long userId) {
        String roomVerify = getRoomVerifyByUserId(userId);
        return ResponseEntity.ok(powerService.selectRecentWeekPowerValueByRoom(roomVerify));
    }

    /**
     * 查询最新电量数据
     *
     * @param userId 用户ID
     * @return 最新电量数据
     */
    @GetMapping("/latest-power")
    public ResponseEntity<Double> getLatestPowerValue(@RequestParam Long userId) {
        String roomVerify = getRoomVerifyByUserId(userId);
        return powerService.getLatestPowerValue(roomVerify);
    }

    /**
     * 根据用户ID获取房间验证标识
     *
     * @param userId 用户ID
     * @return 房间验证标识
     */
    private String getRoomVerifyByUserId(Long userId) {
        User user = userMapper.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        Room room = roomMapper.selectById(user.getRoomId());
        if (room == null) {
            throw new RuntimeException("房间不存在");
        }

        Floor floor = floorMapper.selectById(user.getFloorId());
        if (floor == null) {
            throw new RuntimeException("楼层不存在");
        }

        Building building = buildingMapper.selectById(user.getBuildingId());
        if (building == null) {
            throw new RuntimeException("楼栋不存在");
        }

        return RoomVerifyUtil.generateRoomVerify(building, floor, room);
    }
}
