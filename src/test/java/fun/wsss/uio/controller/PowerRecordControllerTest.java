package fun.wsss.uio.controller;

import fun.wsss.uio.model.PowerRecord;
import fun.wsss.uio.service.PowerRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PowerRecordController.class)
@SpringJUnitConfig
public class PowerRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PowerRecordService powerRecordService;

    @Test
    public void testGetLatestPowerRecord() throws Exception {
        PowerRecord mockRecord = new PowerRecord();
        mockRecord.setId(1L);
        mockRecord.setRoomId(1);
        mockRecord.setPowerValue(new BigDecimal("100.50"));
        mockRecord.setRecordTime(LocalDateTime.now());

        when(powerRecordService.getLatestPowerRecord(any())).thenReturn(mockRecord);

        mockMvc.perform(get("/api/power-records/room/1/latest"))
                .andExpect(status().isOk());
    }
} 