package theModel.JobClasses;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private String EmpName;
    private String EmpPrename;
    private WorkHour EmpWorkHour;

    public Employee()
    {}

    public Employee(String name, String prename, String hourStart, String hourEnd)
    {
        this.EmpName = name;
        this.EmpPrename = prename;
        EmpWorkHour = new WorkHour(UUID.randomUUID().toString(), hourStart, hourEnd);
    }

    public Employee(String name, String prename, WorkHour workHour)
    {
        this.EmpName = name;
        this.EmpPrename = prename;
        EmpWorkHour = workHour;
    }





}
