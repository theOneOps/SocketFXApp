package theModel.JobClasses;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The WorkHour class represents the working hours of an employee.
 * Implements Serializable for object serialization.
 * <p>
 *     The class contains the following attributes:
 *     <ul>
 *         <li>{@code pointing} HashMap: the map containing the work hours with the date as the key and a list of times as the value</li>
 *         <li>{@code serialVersionUID} long: the serial version UID for serialization</li>
 *         </ul>
 *         <p>
 * @see Serializable
 * @see LocalDate
 * @see LocalTime
 */
public class WorkHour implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // A map to store the working hours, with the date as the key and a list of times as the value
    private HashMap<LocalDate, ArrayList<LocalTime>> pointing;

    /**
     * Default constructor that initializes the pointing map.
     */
    public WorkHour() {
        pointing = new HashMap<>();
    }

    /**
     * Adds a work hour for a specific date.
     *
     * @param d the date of the work hour
     * @param h the time of the work hour
     */
    public void addWorkHour(LocalDate d, LocalTime h) {
        if (pointing.containsKey(d)) {
            pointing.get(d).add(h);
        } else {
            ArrayList<LocalTime> refPointing = new ArrayList<>();
            refPointing.add(h);
            pointing.put(d, refPointing);
        }
    }

    /**
     * Gets the pointing map which contains dates and their corresponding work hours.
     *
     * @return the pointing map
     */
    public HashMap<LocalDate, ArrayList<LocalTime>> getPointing() {
        return pointing;
    }

    /**
     * Changes the time of a work hour for a specific date.
     *
     * @param date the date of the work hour to change
     * @param olderHour the old time to replace
     * @param newHour the new time to set
     */
    public void changeLocalTime(String date, String olderHour, String newHour) {
        LocalDate d = LocalDate.parse(date);
        addWorkHour(d, LocalTime.parse(newHour));

        if (pointing.containsKey(d)) {
            int idx = pointing.get(d).indexOf(LocalTime.parse(olderHour));
            if (idx != -1)
                pointing.get(d).remove(LocalTime.parse(olderHour));
        }
    }

    /**
     * Changes the date of a work hour.
     *
     * @param olderDate the old date of the work hour
     * @param newDate the new date to set
     * @param hour the time of the work hour
     */
    public void changeDateWorkHour(String olderDate, String newDate, String hour) {
        addWorkHour(LocalDate.parse(newDate), LocalTime.parse(hour));

        LocalDate olderDateLt = LocalDate.parse(olderDate);

        if (pointing.containsKey(olderDateLt)) {
            int idx = pointing.get(olderDateLt).indexOf(LocalTime.parse(hour));
            if (idx != -1)
                pointing.get(olderDateLt).remove(LocalTime.parse(hour));

            if (pointing.get(olderDateLt).isEmpty())
                pointing.remove(olderDateLt);
        }
    }

    /**
     * Removes a specific work hour for a given date.
     *
     * @param date the date of the work hour to remove
     * @param Hour the time of the work hour to remove
     */
    public void removeLocalTime(String date, String Hour) {
        LocalDate d = LocalDate.parse(date);
        pointing.get(d).remove(LocalTime.parse(Hour));
    }

    /**
     * Sets the pointing map with a new map.
     *
     * @param pointing the new pointing map to set
     */
    public void setPointing(HashMap<LocalDate, ArrayList<LocalTime>> pointing) {
        this.pointing = pointing;
    }

    /**
     * Returns a string representation of the work hours.
     *
     * @return a string representation of the work hours
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (LocalDate date : pointing.keySet()) {
            res.append(String.format("%s %s \n", date.toString(), pointing.get(date).toString()));
        }
        return res.toString();
    }
}
