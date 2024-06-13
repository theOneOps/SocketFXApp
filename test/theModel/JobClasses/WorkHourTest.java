package theModel.JobClasses;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Test class for WorkHour.
 * This class contains unit tests for the WorkHour class.
 */
public class WorkHourTest {
    private final WorkHour workHour = new WorkHour();

    /**
     * Tests the addWorkHour method.
     * Ensures that work hours are correctly added to the WorkHour instance.
     */
    @Test
    public void testAddWorkHour() {
        LocalDate date = LocalDate.of(2023, 6, 5);
        LocalTime time = LocalTime.of(9, 0);

        workHour.addWorkHour(date, time);
        HashMap<LocalDate, ArrayList<LocalTime>> pointing = workHour.getPointing();

        assertTrue(pointing.containsKey(date));
        assertTrue(pointing.get(date).contains(time));
    }

    /**
     * Tests the changeLocalTime method.
     * Ensures that a specific work hour can be changed to a new time.
     */
    @Test
    public void testChangeLocalTime() {
        LocalDate date = LocalDate.of(2023, 6, 5);
        LocalTime oldTime = LocalTime.of(9, 0);
        LocalTime newTime = LocalTime.of(10, 0);

        workHour.addWorkHour(date, oldTime);
        workHour.changeLocalTime(date.toString(), oldTime.toString(), newTime.toString());

        HashMap<LocalDate, ArrayList<LocalTime>> pointing = workHour.getPointing();

        assertTrue(pointing.containsKey(date));
        assertFalse(pointing.get(date).contains(oldTime));
        assertTrue(pointing.get(date).contains(newTime));
    }

    /**
     * Tests the changeDateWorkHour method.
     * Ensures that a specific work hour can be moved to a new date.
     */
    @Test
    public void testChangeDateWorkHour() {
        LocalDate oldDate = LocalDate.of(2023, 6, 5);
        LocalDate newDate = LocalDate.of(2023, 6, 6);
        LocalTime time = LocalTime.of(9, 0);

        workHour.addWorkHour(oldDate, time);
        workHour.changeDateWorkHour(oldDate.toString(), newDate.toString(), time.toString());

        HashMap<LocalDate, ArrayList<LocalTime>> pointing = workHour.getPointing();

        assertFalse(pointing.containsKey(oldDate));
        assertTrue(pointing.containsKey(newDate));
        assertTrue(pointing.get(newDate).contains(time));
    }

    /**
     * Tests the removeLocalTime method.
     * Ensures that a specific work hour can be removed.
     */
    @Test
    public void testRemoveLocalTime() {
        LocalDate date = LocalDate.of(2023, 6, 5);
        LocalTime time = LocalTime.of(9, 0);

        workHour.addWorkHour(date, time);
        workHour.removeLocalTime(date.toString(), time.toString());

        HashMap<LocalDate, ArrayList<LocalTime>> pointing = workHour.getPointing();

        assertTrue(pointing.containsKey(date));
        assertFalse(pointing.get(date).contains(time));
    }

    /**
     * Tests the toString method.
     * Ensures that the WorkHour instance is correctly converted to a string.
     */
    @Test
    public void testToString() {
        LocalDate date1 = LocalDate.of(2023, 6, 5);
        LocalTime time1 = LocalTime.of(9, 0);
        LocalDate date2 = LocalDate.of(2023, 6, 6);
        LocalTime time2 = LocalTime.of(10, 0);

        workHour.addWorkHour(date1, time1);
        workHour.addWorkHour(date2, time2);

        String expectedString = String.format("%s [%s] \n%s [%s] \n", date2, time2.toString(),
                date1, time1.toString());

        assertEquals(expectedString, workHour.toString());
    }
}
