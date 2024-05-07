package theModel.JobClasses;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class WorkHour implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String empID;
    private String hourStart;
    private String hourEnd;
    private String dateWork;

    public String getDateWork() {
        return dateWork;
    }

    public void setDateWork(String dateWork) {
        this.dateWork = dateWork;
    }

    public WorkHour(String id, String start, String end, LocalDate date)
    {
        this.empID = id;
        if(start.isEmpty())
            this.hourStart = "";
        else
            this.hourStart = LocalTime.parse(start).toString();

        if(end.isEmpty())
            this.hourEnd = "";
        else
            this.hourEnd = LocalTime.parse(end).toString();

        this.dateWork = date.toString();
    }

    // Constructor used to print workHour on the tableView
    public WorkHour(String id, String start, String end, String date)
    {
        this.empID = id;
        this.hourStart = LocalTime.parse(start).toString();
        this.hourEnd = LocalTime.parse(end).toString();
        this.dateWork = date;
    }

    public String getEmpID() {
        return empID;
    }

    public void setHourStart(String hourStart) {
        this.hourStart = hourStart;
    }

    public void setHourEnd(String hourEnd) {
        this.hourEnd = hourEnd;
    }

    public String getHourStart() {
        return hourStart;
    }


    public String getHourEnd() {
        return hourEnd;
    }


    @Override
    public String toString()
    {
        return String.format("dateWork : %s hour start %s hour end %s \n",dateWork, hourStart, hourEnd);
    }
}
