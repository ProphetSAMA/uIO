package fun.wsss.uio.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 时间工具类
 *
 * @author Wsssfun
 */
public class Time {
    /**
     * 获取并格式化当前时间
     *
     * @return 当前时间
     */
    public static String getTime() {
        // 获取当前时间并格式化
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }
}
