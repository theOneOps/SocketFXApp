package theView.manage.windowShowEnt;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 * The UtilityWindowShowEnt class provides utility methods for validating date and time strings.
 */
public class UtilityWindowShowEnt
{
    /**
     * Checks if the given time string is valid.
     *
     * @param timeStr the time string to validate
     * @return true if the time string is valid, false otherwise
     */
    public static boolean isValidTime(String timeStr) {
        try {
            LocalTime.parse(timeStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks if the given date string is valid.
     *
     * @param dateStr the date string to validate
     * @return true if the date string is valid, false otherwise
     */
    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
