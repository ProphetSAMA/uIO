package fun.wsss.uio.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 电量实体类
 *
 * @author Wsssfun
 */
@Setter
@Getter
public class Power {

    private String value;
    private String querytime;

    public Power() {
    }

    public Power(Double powerValue, String queryTime) {
        this.value = String.valueOf(powerValue);
        this.querytime = queryTime;
    }


}
