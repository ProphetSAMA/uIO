package fun.wsss.uio.controller;

import fun.wsss.uio.dto.ApiResponse;
import fun.wsss.uio.model.Power;
import fun.wsss.uio.service.power.PowerService;
import fun.wsss.uio.utils.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ApiResponse<List<Power>> selectAllPowerValues() {
        List<Power> powerList = powerService.selectAllPowerValue();
        return ApiResponse.success(powerList, "查询所有电量数据成功");
    }

    /**
     * 查询最近一周电量数据
     *
     * @return 最近一周电量数据
     */
    @GetMapping("/recent-week-power")
    public ApiResponse<List<Power>> selectRecentWeekPowerValues() {
        List<Power> powerList = powerService.selectRecentWeekPowerValue();
        return ApiResponse.success(powerList, "查询最近一周电量数据成功");
    }
    
    /**
     * 查询最新电量数据
     *
     * @return 最新电量数据
     */
    @GetMapping("/latest-power")
    public ApiResponse<Double> getLatestPowerValue() {
        Double value = null;
        try {
            value = powerService.getLatestPowerValue().getBody();
        } catch (Exception e) {
            return ApiResponse.error("获取最新电量数据失败: " + e.getMessage());
        }
        return ApiResponse.success(value, "查询最新电量数据成功");
    }

    /**
     * 查询当前用户的最新电量数据
     * 需要认证
     *
     * @return 当前用户的最新电量数据
     */
    @GetMapping("/me/power/latest")
    public ApiResponse<Double> getUserLatestPower() {
        Long userId = securityUtil.getCurrentUserId();
        if (userId == null) {
            return ApiResponse.unauthorized("未认证或无法获取用户ID");
        }
        try {
            Double value = powerService.getLatestPowerValueByUserId(userId);
            return ApiResponse.success(value, "查询当前用户最新电量数据成功");
        } catch (Exception e) {
            return ApiResponse.error("查询电量数据失败: " + e.getMessage());
        }
    }

    /**
     * 查询当前用户最近一周的电量数据
     * 需要认证
     *
     * @return 当前用户最近一周的电量数据
     */
    @GetMapping("/me/power/history")
    public ApiResponse<List<Power>> getUserRecentWeekPower() {
        Long userId = securityUtil.getCurrentUserId();
        if (userId == null) {
            return ApiResponse.unauthorized("未认证或无法获取用户ID");
        }
        try {
            List<Power> powerList = powerService.selectRecentWeekPowerValueByUserId(userId);
            return ApiResponse.success(powerList, "查询最近一周电量数据成功");
        } catch (Exception e) {
            return ApiResponse.error("查询电量数据失败: " + e.getMessage());
        }
    }

    /**
     * 查询当前用户的所有电量数据
     * 需要认证
     *
     * @return 当前用户的所有电量数据
     */
    @GetMapping("/me/power/all")
    public ApiResponse<List<Power>> getUserAllPower() {
        Long userId = securityUtil.getCurrentUserId();
        if (userId == null) {
            return ApiResponse.unauthorized("未认证或无法获取用户ID");
        }
        try {
            List<Power> powerList = powerService.selectAllPowerValueByUserId(userId);
            return ApiResponse.success(powerList, "查询所有电量数据成功");
        } catch (Exception e) {
            return ApiResponse.error("查询电量数据失败: " + e.getMessage());
        }
    }

}
