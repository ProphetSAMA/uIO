package fun.wsss.uio.Entity;

public class Power {
    String powerValue;
    String queryTime;

    // 构造方法
    public Power(String powerValue, String queryTime) {
        this.powerValue = powerValue;
        this.queryTime = queryTime;
    }

    public String getPowerValue() {
        return powerValue;
    }

    public void setPowerValue(String powerValue) {
        this.powerValue = powerValue;
    }

    public String getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(String queryTime) {
        this.queryTime = queryTime;
    }
}
