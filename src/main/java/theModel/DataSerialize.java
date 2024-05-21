package theModel;

import theModel.JobClasses.Employee;
import theModel.JobClasses.Enterprise;

import java.io.*;
import java.util.HashMap;


public class DataSerialize {

    private HashMap<String, Enterprise> allEnterprises = new HashMap<>();
    private final String fileText = "data.ser";

    public void saveData() throws IOException {
        // save all enterprises
        FileOutputStream fileOut = new FileOutputStream(fileText);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.allEnterprises);
        System.out.println(String.format("Data saved to file %s", fileText));
        out.close();
    }

    public void loadData() throws IOException, ClassNotFoundException {
        // load all enterprises
        File file = new File(fileText);
        if (!file.exists()) {
            System.out.printf("Le fichier  + %s +  n'existe pas.", fileText);
            return;
        }
        FileInputStream fileIn = new FileInputStream(fileText);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.allEnterprises = (HashMap<String, Enterprise>) in.readObject();
        System.out.println(String.format("Data loaded from file %s", fileText));
        in.close();
    }

    public HashMap<String, Enterprise> getAllEnterprises() {
        return this.allEnterprises;
    }

    public void addNewEmployeeToEnterprise(String entName, String empName, String empPrename,
                                           String HourStart, String HourEnd) throws IOException {
        // add employee to enterprise
        this.allEnterprises.get(entName).addEmployee(empName, empPrename, HourStart, HourEnd);
        saveData();
    }

    public void addNewEmployeeToEnterprise(String entName, Employee emp) throws IOException {
        // add employee to enterprise
        this.allEnterprises.get(entName).addEmployee(emp);
        saveData();
    }

    public void removeEmployeeFromEnterprise(String entName, Employee emp) throws IOException
    {
        this.allEnterprises.get(entName).removeEmployee(emp);
        saveData();
    }

    public void addNewEnterprise(String name, String passwd, String port) throws IOException {
        // add new enterprise
        if (!this.allEnterprises.containsKey(name))
        {
            this.allEnterprises.put(name, new Enterprise(name, passwd, port));
            saveData();
        }
    }

//    // to test the pointer workHour in the AppTest main...
//    public void addNewWorkHourForAnEmployee(String entName, String empId, String hourStart, String hourEnd)
//            throws IOException
//    {
//        this.allEnterprises.get(entName).addWorkHourPointerForEmployee(empId, hourStart, hourEnd);
//        saveData();
//    }
//
//    // to test the pointer workHour in the AppTest main...
//    public void addNewWorkHourForAnEmployee(String entName, String empId,
//                                            String hourStart, String hourEnd, LocalDate date)
//            throws IOException
//    {
//        this.allEnterprises.get(entName).addWorkHourPointerForEmployee(empId, hourStart, hourEnd, date);
//        saveData();
//    }

//    // to use when 'check in' on the Pointer
//    public void addNewWorkHourForAnEmployee(String entName, String empId,
//                                            String hour, LocalDate date)
//            throws IOException
//    {
//        this.allEnterprises.get(entName).addWorkHourPointerForEmployee(empId, hour, date);
//        saveData();
//    }

    public void modifyEmpName(String ent, String uuid, String newName) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEmpName(newName);
        saveData();
    }

    public void modifyEmpPrename(String ent, String uuid, String newPrename) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEmpPrename(newPrename);
        saveData();
    }

    public void modifyEmpStartingHour(String ent, String uuid, String startingHour) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setStartingHour(startingHour);
        saveData();
    }

    public void modifyEmpEndingHour(String ent, String uuid, String endingHour) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEndingHour(endingHour);
        saveData();
    }

    public void modifyEmpDepartement(String ent, String uuid, String endingHour) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEndingHour(endingHour);
        saveData();
    }

    @Override
    public String toString() {
        return "DataSerialize{" + "allEnterprises=" + allEnterprises + '}';
    }
}
