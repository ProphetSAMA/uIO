package fun.wsss.uio.controller;

import fun.wsss.uio.model.Power;
import fun.wsss.uio.service.power.PowerService;
import fun.wsss.uio.utils.SecurityUtil;
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
    private final SecurityUtil securityUtil;

    @Autowired
    public PowerController(PowerService powerService, SecurityUtil securityUtil) {
        this.powerService = powerService;
        this.securityUtil = securityUtil;
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

    /**
     * 查询当前用户的最新电量数据
     * 需要认证
     *
     * @return 当前用户的最新电量数据
     */
    @GetMapping("/me/power/latest")
    public ResponseEntity<Double> getUserLatestPower() {
        Long userId = securityUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).body(null);
        }
        Double value = powerService.getLatestPowerValueByUserId(userId);
        return ResponseEntity.ok(value);
    }

    /**
     * 查询当前用户最近一周的电量数据
     * 需要认证
     *
     * @return 当前用户最近一周的电量数据
     */
    @GetMapping("/me/power/history")
    public ResponseEntity<List<Power>> getUserRecentWeekPower() {
        Long userId = securityUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).body(null);
        }
        List<Power> powerList = powerService.selectRecentWeekPowerValueByUserId(userId);
        return ResponseEntity.ok(powerList);
    }

    /**
     * 查询当前用户的所有电量数据
     * 需要认证
     *
     * @return 当前用户的所有电量数据
     */
    @GetMapping("/me/power/all")
    public ResponseEntity<List<Power>> getUserAllPower() {
        Long userId = securityUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseEntity.status(401).body(null);
        }
        List<Power> powerList = powerService.selectAllPowerValueByUserId(userId);
        return ResponseEntity.ok(powerList);
    }

}
