package fun.wsss.uio.Controller;

import fun.wsss.uio.model.Power;
import fun.wsss.uio.service.PowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class PowerController {

    private final PowerService powerService;

    @Autowired
    public PowerController(PowerService powerService) {
        this.powerService = powerService;
    }
    // 所有数据
    @GetMapping("/all-power")
    public ResponseEntity<List<Power>> selectAllPowerValues() {
        return powerService.selectAllPowerValue();
    }
    // 最新一条数据
    @GetMapping("/latest-power")
    public ResponseEntity<Double> getLatestPowerValue() {
        return powerService.getLatestPowerValue();
    }

}
