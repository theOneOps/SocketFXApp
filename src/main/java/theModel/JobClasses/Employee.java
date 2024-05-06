package theModel.JobClasses;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String empName;
    private String empPrename;
    private String startingHour;
    private String endingHour;

    public String getUuid() {
        return uuid;
    }

    public String getStartingHour() {
        return startingHour;
    }

    public String getEndingHour() {
        return endingHour;
    }

    public Employee(String name, String prename, String hourStart, String hourEnd)
    {
        this.uuid = UUID.randomUUID().toString();
        this.empName = name;
        this.empPrename = prename;
        this.startingHour = LocalTime.parse(hourStart).toString();
        this.endingHour = LocalTime.parse(hourEnd).toString();
    }

    public Employee(String uuid, String name, String prename, String hourStart, String hourEnd)
    {
        this.uuid = uuid;
        this.empName = name;
        this.empPrename = prename;
        this.startingHour = LocalTime.parse(hourStart).toString();
        this.endingHour = LocalTime.parse(hourEnd).toString();
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setEmpPrename(String empPrename) {
        this.empPrename = empPrename;
    }

    public String getEmpName() {
        return empName;
    }

    public String getEmpPrename() {
        return empPrename;
    }

    @Override
    public String toString()
    {
        return String.format("name : %s prename : %s \n", empName, empPrename);
    }

}
