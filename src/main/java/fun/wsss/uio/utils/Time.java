package fun.wsss.uio.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    public static String getTime() {
        // 获取当前时间并格式化
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
}
