package theModel.JobClasses;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * The Enterprise class represents an enterprise with a name, a port, and a collection of employees.
 * Implements Serializable for object serialization.
 * <p>
 *     The Enterprise class has the following attributes:
 *     <ul>
 *         <li>{@code Entname} String: the name of the enterprise</li>
 *         <li>{@code employees} HashMap(String, Enterprise): the collection of employees in the enterprise</li>
 *         <li>{@code EntPort} String: the port of the enterprise</li>
 *     </ul>
 *     <p>
 * @see Employee
 * @see Serializable
 * @see HashMap
 * @see Employee
 */
public class Enterprise implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String Entname; // The name of the enterprise
    private HashMap<String, Employee> employees = new HashMap<>(); // The collection of employees in the enterprise
    private String EntPort; // The port of the enterprise

    /**
     * Default constructor for Enterprise.
     */
    public Enterprise() {}

    /**
     * Constructs an Enterprise instance with a specified name and port.
     *
     * @param name the name of the enterprise
     * @param port the port of the enterprise
     */
    public Enterprise(String name, String port) {
        if (port.matches("\\d+")) {
            this.Entname = name;
            this.EntPort = port;
        }
    }

    /**
     * Gets the name of the enterprise.
     *
     * @return the name of the enterprise
     */
    public String getEntname() {
        return Entname;
    }

    /**
     * Gets the collection of employees in the enterprise.
     *
     * @return the collection of employees
     */
    public HashMap<String, Employee> getEmployees() {
        return employees;
    }

    /**
     * Adds an employee to the enterprise with specified details.
     *
     * @param empName   the name of the employee
     * @param empPrename the prename of the employee
     * @param HourStart the starting hour of the employee
     * @param HourEnd   the ending hour of the employee
     */
    public void addEmployee(String empName, String empPrename, String HourStart, String HourEnd) {
        // Add employee to enterprise
        Employee emp = new Employee(empName, empPrename, HourStart, HourEnd, "");
        this.getEmployees().put(emp.getUuid(), emp);
    }

    /**
     * Adds an employee to the enterprise with specified details including department.
     *
     * @param empName    the name of the employee
     * @param empPrename the prename of the employee
     * @param HourStart  the starting hour of the employee
     * @param HourEnd    the ending hour of the employee
     * @param department the department of the employee
     */
    public void addEmployee(String empName, String empPrename, String HourStart,
                            String HourEnd, String department) {
        // Add employee to enterprise
        Employee emp = new Employee(empName, empPrename, HourStart, HourEnd, department);
        this.getEmployees().put(emp.getUuid(), emp);
    }

    /**
     * Adds an existing employee to the enterprise.
     *
     * @param emp the employee to add
     */
    public void addEmployee(Employee emp) {
        // Add employee to enterprise
        this.getEmployees().put(emp.getUuid(), emp);
    }

    /**
     * Removes an employee from the enterprise.
     *
     * @param emp the employee to remove
     */
    public void removeEmployee(Employee emp) {
        this.getEmployees().remove(emp.getUuid());
    }

    /**
     * Gets the names of all employees in the enterprise.
     *
     * @return a list of employee names
     */
    public ArrayList<String> getAllEmployeesName() {
        ArrayList<String> AllEmployeesName = new ArrayList<>();
        if (employees.isEmpty()) {
            AllEmployeesName.add("No employees");
        } else {
            for (String id : employees.keySet()) {
                AllEmployeesName.add(String.format("%s %s (%s) ", employees.get(id).getEmpName(),
                        employees.get(id).getEmpPrename(), id));
            }
        }
        return AllEmployeesName;
    }

    /**
     * Returns a string representation of the enterprise.
     *
     * @return a string representation of the enterprise
     */
    @Override
    public String toString() {
        return String.format("enterprise : %s | employees : %s ", Entname, employees);
    }

    /**
     * Gets the port of the enterprise.
     *
     * @return the port of the enterprise
     */
    public String getEntPort() {
        return EntPort;
    }

    /**
     * Sets the port of the enterprise.
     *
     * @param entPort the port to set
     */
    public void setEntPort(String entPort) {
        EntPort = entPort;
    }

    /**
     * Sets the name of the enterprise.
     *
     * @param entname the name to set
     */
    public void setEntname(String entname) {
        Entname = entname;
    }

    /**
     * Checks if two enterprises are equal.
     *
     * @param o the object to compare
     * @return true if the enterprises are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enterprise that = (Enterprise) o;
        return Objects.equals(Entname, that.getEntname()) &&
                Objects.equals(EntPort, that.getEntPort()) &&
                Objects.equals(employees, that.employees);
    }

    /**
     * Returns the hash code of the enterprise.
     *
     * @return the hash code of the enterprise
     */
    @Override
    public int hashCode() {
        return Objects.hash(Entname, EntPort, employees);
    }
}
