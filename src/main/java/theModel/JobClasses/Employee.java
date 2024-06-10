package theModel.JobClasses;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

/**
 * The Employee class represents an employee with their personal details and work hours.
 * Implements Serializable for object serialization.
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;           // Unique identifier for the employee
    private String empName;        // Employee's name
    private String empPrename;     // Employee's prename
    private String emDep = "";     // Employee's department
    private String startingHour;   // Employee's starting work hour
    private String endingHour;     // Employee's ending work hour
    private WorkHour workHour = new WorkHour(); // Employee's work hour details

    /**
     * Constructs an Employee instance with specified details.
     *
     * @param name       the employee's name
     * @param prename    the employee's prename
     * @param hourStart  the starting hour of the employee
     * @param hourEnd    the ending hour of the employee
     * @param department the department of the employee
     */
    public Employee(String name, String prename, String hourStart, String hourEnd, String department) {
        this.uuid = UUID.randomUUID().toString();
        this.empName = name;
        this.empPrename = prename;
        this.startingHour = LocalTime.parse(hourStart).toString();
        this.endingHour = LocalTime.parse(hourEnd).toString();
        this.emDep = department;
    }

    /**
     * Constructs an Employee instance with specified UUID and details.
     *
     * @param uuid       the unique identifier of the employee
     * @param name       the employee's name
     * @param prename    the employee's prename
     * @param hourStart  the starting hour of the employee
     * @param hourEnd    the ending hour of the employee
     * @param department the department of the employee
     */
    public Employee(String uuid, String name, String prename, String hourStart, String hourEnd, String department) {
        this.uuid = uuid;
        this.empName = name;
        this.empPrename = prename;
        this.startingHour = LocalTime.parse(hourStart).toString();
        this.endingHour = LocalTime.parse(hourEnd).toString();
        this.emDep = department;
    }

    /**
     * Constructs an Employee instance with specified UUID, details, and work hour information.
     *
     * @param uuid       the unique identifier of the employee
     * @param name       the employee's name
     * @param prename    the employee's prename
     * @param hourStart  the starting hour of the employee
     * @param hourEnd    the ending hour of the employee
     * @param department the department of the employee
     * @param wh         the work hour details of the employee
     */
    public Employee(String uuid, String name, String prename, String hourStart,
                    String hourEnd, String department, WorkHour wh) {
        this.uuid = uuid;
        this.empName = name;
        this.empPrename = prename;
        this.startingHour = LocalTime.parse(hourStart).toString();
        this.endingHour = LocalTime.parse(hourEnd).toString();
        this.emDep = department;
        this.workHour = wh;
    }

    /**
     * Sets the employee's name.
     *
     * @param empName the name to set
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /**
     * Sets the employee's prename.
     *
     * @param empPrename the prename to set
     */
    public void setEmpPrename(String empPrename) {
        this.empPrename = empPrename;
    }

    /**
     * Gets the employee's name.
     *
     * @return the employee's name
     */
    public String getEmpName() {
        return empName;
    }

    /**
     * Gets the employee's prename.
     *
     * @return the employee's prename
     */
    public String getEmpPrename() {
        return empPrename;
    }

    /**
     * Gets the unique identifier of the employee.
     *
     * @return the employee's UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Gets the starting hour of the employee.
     *
     * @return the employee's starting hour
     */
    public String getStartingHour() {
        return startingHour;
    }

    /**
     * Gets the ending hour of the employee.
     *
     * @return the employee's ending hour
     */
    public String getEndingHour() {
        return endingHour;
    }

    /**
     * Gets the department of the employee.
     *
     * @return the employee's department
     */
    public String getEmDep() {
        return emDep;
    }

    /**
     * Sets the department of the employee.
     *
     * @param emDep the department to set
     */
    public void setEmDep(String emDep) {
        this.emDep = emDep;
    }

    /**
     * Gets the work hour details of the employee.
     *
     * @return the employee's work hour details
     */
    public WorkHour getWorkHour() {
        return workHour;
    }

    /**
     * Sets the work hour details of the employee.
     *
     * @param workHour the work hour details to set
     */
    public void setWorkHour(WorkHour workHour) {
        this.workHour = workHour;
    }

    /**
     * Sets the starting hour of the employee.
     *
     * @param startingHour the starting hour to set
     */
    public void setStartingHour(String startingHour) {
        this.startingHour = startingHour;
    }

    /**
     * Sets the ending hour of the employee.
     *
     * @param endingHour the ending hour to set
     */
    public void setEndingHour(String endingHour) {
        this.endingHour = endingHour;
    }

    /**
     * Returns a string representation of the employee.
     *
     * @return a string representation of the employee
     */
    @Override
    public String toString() {
        return String.format("name : %s prename : %s workhour : %s \n", empName, empPrename, workHour);
    }
}
