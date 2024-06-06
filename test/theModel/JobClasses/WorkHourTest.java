package theModel.JobClasses;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class WorkHourTest {
    private final WorkHour workHour = new WorkHour();

    @Test
    public void testAddWorkHour() {
        LocalDate date = LocalDate.of(2023, 6, 5);
        LocalTime time = LocalTime.of(9, 0);

        workHour.addWorkHour(date, time);
        HashMap<LocalDate, ArrayList<LocalTime>> pointing = workHour.getPointing();

        assertTrue(pointing.containsKey(date));
        assertTrue(pointing.get(date).contains(time));
    }

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
