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

    public void setEntname(String entname) {
        Entname = entname;
    }

    public void setEntpasswd(String entpasswd) {
        Entpasswd = entpasswd;
    }

    // to create new enterprise
    public Enterprise(String name, String passwd)
    {
        this.Entname = name;
        this.Entpasswd = passwd;
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
        this.getEmployees().put(emp.getUuid(), emp);
    }

    public void addEmployee(Employee emp) {
        // add employee to enterprise
        this.getEmployees().put(emp.getUuid(), emp);
    }

    public void removeEmployee(Employee emp)
    {
        this.getEmployees().remove(emp.getUuid());
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

    public String ConvertHashMapToString()
    {
        StringBuilder res = new StringBuilder();
        for (LocalDate lt : workHours.keySet())
        {
            res.append(String.format("date : %s %s \n", lt.toString(), workHours.get(lt)));
        }
        return res.toString();
    }

    @Override
    public String toString() {
        return STR."entreprise : \{Entname} \{Entpasswd} employees \{employees} workhours \{ConvertHashMapToString()}";
    }
}
