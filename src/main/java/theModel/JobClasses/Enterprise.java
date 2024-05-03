package theModel.JobClasses;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class Enterprise implements Serializable {

    private static final long serialVersionUID = 1L;

    private String Entname;
    private HashMap<String, Employee> employees = new HashMap<>();
    private HashMap<LocalDate, ArrayList<WorkHour>> workHours = new HashMap<>();
    private String Entpasswd;


    public Enterprise()
    {}

    // to create new enterprise
    public Enterprise(String name, String passwd)
    {
        this.Entname = name;
        this.Entpasswd = passwd;
    }

    // to load an enterprise
    public Enterprise(String name,String passwd, HashMap<String, Employee> employees,
                      HashMap<LocalDate, ArrayList<WorkHour>> workHours)
    {
        this.Entname = name;
        this.Entpasswd = passwd;
        this.employees = employees;
        this.workHours = workHours;
    }

    public String getEntname() {
        return Entname;
    }

    public HashMap<LocalDate, ArrayList<WorkHour>> getWorkHours() {
        return workHours;
    }

    public HashMap<String, Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(String empName, String empPrename, String HourStart, String HourEnd) {
        // add employee to enterprise
        Employee emp = new Employee(empName, empPrename, HourStart, HourEnd);
        this.getEmployees().put(empName, emp);
    }

    public void addWorkHourPointerForEmployee(String empId, String HourStart, String HourEnd) {
        // add work hour to enterprise
        WorkHour workHour = new WorkHour(empId, HourStart, HourEnd);
        LocalDate date = LocalDate.now();
        if (this.getWorkHours().containsKey(date)) {
            this.getWorkHours().get(date).add(workHour);
        } else {
            ArrayList<WorkHour> workHours = new ArrayList<>();
            workHours.add(workHour);
            this.getWorkHours().put(date, workHours);
        }
    }

    public String getEntpasswd() {
        return Entpasswd;
    }

    @Override
    public String toString() {
        return "entreprise : " + Entname + " " + Entpasswd + " employees " + employees + "\n " + workHours;
    }
}
