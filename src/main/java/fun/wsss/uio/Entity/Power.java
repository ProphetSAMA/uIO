package fun.wsss.uio.Entity;

public class Power {
//    private Long id;
    private String value;
    private String querytime;

    // 构造方法
    public Power(Double powerValue, String queryTime) {
        this.value = String.valueOf(powerValue);
        this.querytime = queryTime;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getQuerytime() {
        return querytime;
    }

    public void setQuerytime(String querytime) {
        this.querytime = querytime;
    }
}
