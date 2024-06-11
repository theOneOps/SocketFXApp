package theModel.JobClasses;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The WorkHourEntry class represents a single work hour entry with a date and time.
 * This class is used to print work hours of employees on the TableView of the central application.
 * <p>
 *     The class contains the following attributes:
 *     <ul>
 *         <li>{@code date} String: the date of the work hour entry</li>
 *         <li>{@code time} String: the time of the work hour entry</li>
 *         </ul>
 *         <p>
 * @see WorkHour
 * @see String
 *
 */
public class WorkHourEntry {
    private String date; // The date of the work hour entry
    private String time; // The time of the work hour entry

    /**
     * Constructs a WorkHourEntry instance with specified date and time as strings.
     *
     * @param theDate the date of the work hour entry
     * @param theTime the time of the work hour entry
     */
    public WorkHourEntry(String theDate, String theTime) {
        this.date = theDate;
        this.time = theTime;
    }

    /**
     * Constructs a WorkHourEntry instance with specified date and time as LocalDate and LocalTime.
     *
     * @param date the date of the work hour entry
     * @param time the time of the work hour entry
     */
    public WorkHourEntry(LocalDate date, LocalTime time) {
        this.date = date.toString();
        this.time = time.toString();
    }

    /**
     * Sets the date of the work hour entry.
     *
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets the time of the work hour entry.
     *
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets the date of the work hour entry.
     *
     * @return the date of the work hour entry
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the time of the work hour entry.
     *
     * @return the time of the work hour entry
     */
    public String getTime() {
        return time;
    }

    /**
     * Returns a string representation of the work hour entry.
     *
     * @return a string representation of the work hour entry
     */
    @Override
    public String toString() {
        return String.format("date -> %s | time -> %s \n", date, time);
    }
}
