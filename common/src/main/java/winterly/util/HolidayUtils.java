package winterly.util;

import java.time.LocalDate;

public final class HolidayUtils {

    public static boolean isWinterHolidays() {
        LocalDate date = LocalDate.now();
        int month = date.getMonth().getValue();

		if(month == 12 && date.getDayOfMonth() >= 25) {
			return true;
		}
        return month == 1 && date.getDayOfMonth() <= 7;
    }
}
