package theModel.JobClasses;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


public class WorkHourEntry {
    private LocalDate date;
    private LocalTime time;

    public WorkHourEntry(LocalDate date, LocalTime time) {
        this.date = date;
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format("date -> %s | time -> %s \n", date.toString(), time.toString());
    }
}