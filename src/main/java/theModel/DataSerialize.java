package theModel;

import theModel.JobClasses.Employee;
import theModel.JobClasses.Enterprise;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * The DataSerialize class handles the serialization and deserialization of enterprise data,
 * including saving and loading enterprises and their employees.
 */
public class DataSerialize {

    private HashMap<String, Enterprise> allEnterprises = new HashMap<>(); // Map of all enterprises
    private String fileText = "data.ser"; // The file to save/load data

    /**
     * Saves the current state of all enterprises to a file.
     *
     * @throws IOException if an I/O error occurs during saving
     */
    public void saveData() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(fileText);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.allEnterprises);
        System.out.printf("Data saved to file %s \n", fileText);
        out.close();
    }

    /**
     * Loads the state of all enterprises from a file.
     *
     * @throws IOException            if an I/O error occurs during loading
     * @throws ClassNotFoundException if the class for the serialized object cannot be found
     */
    public void loadData() throws IOException, ClassNotFoundException {
        File file = new File(fileText);
        if (!file.exists()) {
            System.out.printf("The file %s does not exist. \n", fileText);
            return;
        }
        FileInputStream fileIn = new FileInputStream(fileText);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.allEnterprises = (HashMap<String, Enterprise>) in.readObject();
        System.out.printf("Data loaded from file %s \n", fileText);
        in.close();
    }

    /**
     * Gets all enterprises.
     *
     * @return a map of all enterprises
     */
    public HashMap<String, Enterprise> getAllEnterprises() {
        return this.allEnterprises;
    }

    /**
     * Gets an enterprise by its name.
     *
     * @param entName the name of the enterprise
     * @return the enterprise with the specified name
     */
    public Enterprise getEntByName(String entName) {
        return allEnterprises.get(entName);
    }

    /**
     * Adds a new employee to an enterprise by name.
     *
     * @param entName   the name of the enterprise
     * @param empName   the name of the employee
     * @param empPrename the prename of the employee
     * @param HourStart the starting hour of the employee
     * @param HourEnd   the ending hour of the employee
     * @throws IOException if an I/O error occurs during saving
     */
    public void addNewEmployeeToEnterprise(String entName, String empName, String empPrename,
                                           String HourStart, String HourEnd) throws IOException {
        this.allEnterprises.get(entName).addEmployee(empName, empPrename, HourStart, HourEnd);
        saveData();
    }

    /**
     * Adds a new employee to an enterprise.
     *
     * @param entName the name of the enterprise
     * @param emp     the employee to add
     * @throws IOException if an I/O error occurs during saving
     */
    public void addNewEmployeeToEnterprise(String entName, Employee emp) throws IOException {
        this.allEnterprises.get(entName).addEmployee(emp);
        saveData();
    }

    /**
     * Removes an employee from an enterprise.
     *
     * @param entName the name of the enterprise
     * @param emp     the employee to remove
     * @throws IOException if an I/O error occurs during saving
     */
    public void removeEmployeeFromEnterprise(String entName, Employee emp) throws IOException {
        this.allEnterprises.get(entName).removeEmployee(emp);
        saveData();
    }

    /**
     * Adds a new enterprise.
     *
     * @param name the name of the enterprise
     * @param port the port of the enterprise
     * @throws IOException if an I/O error occurs during saving
     */
    public void addNewEnterprise(String name, String port) throws IOException {
        if (!this.allEnterprises.containsKey(name)) {
            this.allEnterprises.put(name, new Enterprise(name, port));
            saveData();
        }
    }

    /**
     * Modifies the name of an employee.
     *
     * @param ent      the name of the enterprise
     * @param uuid     the UUID of the employee
     * @param newName  the new name of the employee
     * @throws IOException if an I/O error occurs during saving
     */
    public void modifyEmpName(String ent, String uuid, String newName) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEmpName(newName);
        saveData();
    }

    /**
     * Modifies the prename of an employee.
     *
     * @param ent       the name of the enterprise
     * @param uuid      the UUID of the employee
     * @param newPrename the new prename of the employee
     * @throws IOException if an I/O error occurs during saving
     */
    public void modifyEmpPrename(String ent, String uuid, String newPrename) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEmpPrename(newPrename);
        saveData();
    }

    /**
     * Modifies the starting hour of an employee.
     *
     * @param ent          the name of the enterprise
     * @param uuid         the UUID of the employee
     * @param startingHour the new starting hour of the employee
     * @throws IOException if an I/O error occurs during saving
     */
    public void modifyEmpStartingHour(String ent, String uuid, String startingHour) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setStartingHour(startingHour);
        saveData();
    }

    /**
     * Modifies the ending hour of an employee.
     *
     * @param ent        the name of the enterprise
     * @param uuid       the UUID of the employee
     * @param endingHour the new ending hour of the employee
     * @throws IOException if an I/O error occurs during saving
     */
    public void modifyEmpEndingHour(String ent, String uuid, String endingHour) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEndingHour(endingHour);
        saveData();
    }

    /**
     * Modifies the department of an employee.
     *
     * @param ent       the name of the enterprise
     * @param uuid      the UUID of the employee
     * @param department the new department of the employee
     * @throws IOException if an I/O error occurs during saving
     */
    public void modifyEmpDepartement(String ent, String uuid, String department) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEmDep(department);
        saveData();
    }

    /**
     * Adds a new work hour entry for an employee.
     *
     * @param port       the port of the enterprise
     * @param empId      the ID of the employee
     * @param dateDay    the date of the work hour
     * @param hourStart  the starting time of the work hour
     * @throws IOException if an I/O error occurs during saving
     */
    public void addNewWorkHour(String port, String empId, String dateDay, String hourStart) throws IOException {
        if (getEnterpriseClassByPort(port) != null) {
            String entName = getEnterpriseClassByPort(port).getEntname();
            if (this.allEnterprises.get(entName).getEmployees().get(empId) != null) {
                this.allEnterprises.get(entName).getEmployees()
                        .get(empId).getWorkHour().addWorkHour(LocalDate.parse(dateDay), LocalTime.parse(hourStart));
                saveData();
            }
        }
    }

    /**
     * Modifies the time of a work hour entry for an employee.
     *
     * @param entPort    the port of the enterprise
     * @param empId      the ID of the employee
     * @param dateDay    the date of the work hour
     * @param olderHour  the old time of the work hour
     * @param newHour    the new time of the work hour
     * @throws IOException if an I/O error occurs during saving
     */
    public void modifyTimeWorkHour(String entPort, String empId, String dateDay,
                                   String olderHour, String newHour) throws IOException {
        if (getEnterpriseClassByPort(entPort) != null) {
            String entName = getEnterpriseClassByPort(entPort).getEntname();
            this.allEnterprises.get(entName).getEmployees().get(empId)
                    .getWorkHour().changeLocalTime(dateDay, olderHour, newHour);
            saveData();
        }
    }

    /**
     * Modifies the date of a work hour entry for an employee.
     *
     * @param entPort   the port of the enterprise
     * @param empId     the ID of the employee
     * @param olderDate the old date of the work hour
     * @param newDate   the new date of the work hour
     * @param newHour   the new time of the work hour
     * @throws IOException if an I/O error occurs during saving
     */
    public void modifyDateWorkHour(String entPort, String empId, String olderDate,
                                   String newDate, String newHour) throws IOException {
        if (getEnterpriseClassByPort(entPort) != null) {
            String entName = getEnterpriseClassByPort(entPort).getEntname();
            this.allEnterprises.get(entName).getEmployees().get(empId)
                    .getWorkHour().changeDateWorkHour(olderDate, newDate, newHour);
            saveData();
        }
    }

    /**
     * Removes a work hour entry for an employee.
     *
     * @param entPort the port of the enterprise
     * @param empId   the ID of the employee
     * @param date    the date of the work hour
     * @param hour    the time of the work hour
     * @throws IOException if an I/O error occurs during saving
     */
    public void removeWorkHour(String entPort, String empId, String date, String hour) throws IOException {
        if (getEnterpriseClassByPort(entPort) != null) {
            String entName = getEnterpriseClassByPort(entPort).getEntname();
            this.allEnterprises.get(entName).getEmployees()
                    .get(empId).getWorkHour().removeLocalTime(date, hour);
            saveData();
        }
    }

    /**
     * Gets the enterprise by its port.
     *
     * @param port the port of the enterprise
     * @return the enterprise with the specified port, or null if not found
     */
    public Enterprise getEnterpriseClassByPort(String port) {
        for (Enterprise ent : this.allEnterprises.values()) {
            if (ent.getEntPort().equals(port))
                return ent;
        }
        return null;
    }

    /**
     * Changes the port of an enterprise.
     *
     * @param entName the name of the enterprise
     * @param newPort the new port to set
     * @throws IOException if an I/O error occurs during saving
     */
    public void changeEntPort(String entName, String newPort) throws IOException {
        allEnterprises.get(entName).setEntPort(newPort);
        saveData();
    }

    /**
     * Changes the name of an enterprise.
     *
     * @param oldName the old name of the enterprise
     * @param newName the new name to set
     * @throws IOException if an I/O error occurs during saving
     */
    public void changeEntName(String oldName, String newName) throws IOException {
        allEnterprises.get(oldName).setEntname(newName);
        allEnterprises.put(newName, allEnterprises.get(oldName));
        allEnterprises.remove(oldName);
        saveData();
    }

    /**
     * Returns a string representation of all enterprises.
     *
     * @return a string representation of all enterprises
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder(" allEnterprises from DataSerialize : \n");
        for (String s : allEnterprises.keySet()) {
            res.append(String.format("entName %s -> %s \n", s, allEnterprises.get(s)));
        }
        return res.toString();
    }

    /**
     * Sets the file text for serialization.
     *
     * @param fileText the file text to set
     */
    public void setFileText(String fileText) {
        this.fileText = fileText;
    }
}
