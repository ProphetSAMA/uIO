package fun.wsss.uio.controller;

import fun.wsss.uio.model.Power;
import fun.wsss.uio.service.power.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Autowired
    public PowerController(PowerService powerService) {
        this.powerService = powerService;
    }

    /**
     * 查询所有电量数据
     *
     * @return 电量数据列表
     */
    @GetMapping("/all-power")
    public ResponseEntity<List<Power>> selectAllPowerValues() {
        List<Power> powerList = powerService.selectAllPowerValue();
        return ResponseEntity.ok(powerList);
    }

    /**
     * 查询最近一周电量数据
     *
     * @return 最近一周电量数据
     */
    @GetMapping("/recent-week-power")
    public ResponseEntity<List<Power>> selectRecentWeekPowerValues() {
        List<Power> powerList = powerService.selectRecentWeekPowerValue();
        return ResponseEntity.ok(powerList);
    }
    
    /**
     * 查询最新电量数据
     *
     * @return 最新电量数据
     */
    @GetMapping("/latest-power")
    public ResponseEntity<Double> getLatestPowerValue() {
        return powerService.getLatestPowerValue();
    }

}
