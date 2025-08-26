package fun.wsss.uio.controller;

import fun.wsss.uio.model.PowerRecord;
import fun.wsss.uio.service.PowerRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Api(tags = "电量记录管理")
@RestController
@RequestMapping("/api/power-records")
@RequiredArgsConstructor
public class PowerRecordController {
    private final PowerRecordService powerRecordService;

    @ApiOperation("获取房间最新电量记录")
    @GetMapping("/room/{roomId}/latest")
    public PowerRecord getLatestPowerRecord(
            @ApiParam("房间ID") @PathVariable Integer roomId) {
        return powerRecordService.getLatestPowerRecord(roomId);
    }

    @ApiOperation("获取房间指定时间范围的电量记录")
    @GetMapping("/room/{roomId}")
    public List<PowerRecord> getPowerRecords(
            @ApiParam("房间ID") @PathVariable Integer roomId,
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return powerRecordService.getPowerRecords(roomId, startTime, endTime);
    }

    @ApiOperation("获取所有房间指定时间范围的电量记录")
    @GetMapping("/all")
    public List<PowerRecord> getAllPowerRecords(
            @ApiParam("开始时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @ApiParam("结束时间") @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) {
        return powerRecordService.getAllPowerRecords(startTime, endTime);
    }

    @ApiOperation("更新指定房间的电量记录")
    @PostMapping("/room/{roomId}/update")
    public void updateRoomPower(
            @ApiParam("房间ID") @PathVariable Integer roomId) {
        powerRecordService.updateRoomPower(roomId);
    }

    @ApiOperation("更新所有房间的电量记录")
    @PostMapping("/update-all")
    public void updateAllRoomsPower() {
        powerRecordService.updateAllRoomsPower();
    }
} 