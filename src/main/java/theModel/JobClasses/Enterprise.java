package theModel.JobClasses;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

public class Enterprise implements Serializable {

    private static final long serialVersionUID = 1L;
    private String Entname;
    private Employee[] employees;
    private HashMap<LocalDate, WorkHour[]> workHours = new HashMap<>();
    private String port;
    private String ip;

    public Enterprise()
    {}

    // to create new enterprise
    public Enterprise(String name, String port, String ip)
    {
        this.Entname = name;
        this.port = port;
        this.ip = ip;
    }

    // to load an enterprise
    public Enterprise(String name, String port, String ip, Employee[] employees,
                      HashMap<LocalDate, WorkHour[]> workHours)
    {
        this.Entname = name;
        this.port = port;
        this.ip = ip;
        this.employees = employees;
        this.workHours = workHours;
    }
}
