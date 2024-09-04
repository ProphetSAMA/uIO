package fun.wsss.uio.Controller;

import fun.wsss.uio.Entity.Power;
import fun.wsss.uio.Service.PowerService;
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
public class PowerControl {

    private final PowerService powerService;

    @Autowired
    public PowerControl(PowerService powerService) {
        this.powerService = powerService;
    }
    @GetMapping("/power")
    public ResponseEntity<List<Power>> getPowerValues() {
        return powerService.getPowerValue();
    }
}
