package Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {


    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime addHours(LocalDateTime date, long hours) {
        return date.plusHours(hours);
    }


}
