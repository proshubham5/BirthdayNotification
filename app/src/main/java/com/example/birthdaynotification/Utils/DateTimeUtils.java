package com.example.birthdaynotification.Utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;

public final class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static LocalDate convertFromEpochMillisToLocalDate(Long millis) {
        Instant i = Instant.ofEpochMilli(millis);
        return i.atZone(ZoneOffset.UTC).toLocalDate();
    }

    public static String getDateStringFromLocalDate(LocalDate date) {
        return date.getDayOfMonth() + " " + date.getMonth() + " " + date.getYear();
    }

    public static Long getEpochMillisFromLocalDate(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}

