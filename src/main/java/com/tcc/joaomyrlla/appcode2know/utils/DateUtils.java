package com.tcc.joaomyrlla.appcode2know.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String toPattern(String pattern, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static Date toDate(String pattern, String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
