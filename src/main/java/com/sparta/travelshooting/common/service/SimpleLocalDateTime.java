package com.sparta.travelshooting.common.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SimpleLocalDateTime {
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String dateTimeFormat(LocalDateTime time){
        return dateTimeFormatter.format(time);
    }
}
