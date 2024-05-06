package theModel.JobClasses;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

public class WorkHour implements Serializable {

    private static final long serialVersionUID = 1L;

    private String empID;
    private LocalTime HourStart;
    private LocalTime HourEnd;

    private transient StringProperty empPropertyId;
    private transient StringProperty empStartHour;
    private transient StringProperty empEndHour;


    public WorkHour(String id, String start, String end)
    {
        this.empID = id;
        this.HourStart = LocalTime.parse(start);
        this.HourEnd = LocalTime.parse(end);

        this.empPropertyId = new SimpleStringProperty(empID);
        this.empStartHour = new SimpleStringProperty(HourStart.toString());
        this.empEndHour = new SimpleStringProperty(HourEnd.toString());
    }

    public String empStartHour() {
        return empStartHour.get();
    }

    public String getEmpEndHour() {
        return empEndHour.get();
    }

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(UUID empID) {
        this.empID = empID.toString();
        this.empPropertyId.set(empID.toString());
    }

    public LocalTime getHourStart() {
        return HourStart;
    }

    public void setHourStart(LocalTime hourStart) {
        HourStart = hourStart;
        this.empStartHour.set(hourStart.toString());
    }

    public LocalTime getHourEnd() {
        return HourEnd;
    }

    public void setHourEnd(LocalTime hourEnd) {
        HourEnd = hourEnd;
        this.empEndHour.set(hourEnd.toString());
    }

    @Override
    public String toString()
    {
        return String.format("hour start %s hour end %s \n", HourStart.toString(), HourEnd.toString());
    }
}
