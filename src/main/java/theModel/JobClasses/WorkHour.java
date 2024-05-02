package theModel.JobClasses;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

public class WorkHour implements Serializable {

    private static final long serialVersionUID = 1L;
    private String empID;
    private LocalTime HourStart;
    private LocalTime HourEnd;

    public WorkHour(String id, LocalTime start, LocalTime end)
    {
        this.empID = id;
        this.HourStart = start;
        this.HourEnd = end;
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(UUID empID) {
        this.empID = empID.toString();
    }

    public LocalTime getHourStart() {
        return HourStart;
    }

    public void setHourStart(LocalTime hourStart) {
        HourStart = hourStart;
    }

    public LocalTime getHourEnd() {
        return HourEnd;
    }

    public void setHourEnd(LocalTime hourEnd) {
        HourEnd = hourEnd;
    }
}
