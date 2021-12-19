package ru.tlmclub.winterly.util;

import java.time.LocalDate;
import java.time.temporal.ChronoField;

public class HolidayUtils {
    protected HolidayUtils() {}

    public static boolean isWinter() {
        LocalDate date = LocalDate.now();
        int month = date.get(ChronoField.MONTH_OF_YEAR);
        return month == 12 || month == 1 || month == 2;
    }
}
