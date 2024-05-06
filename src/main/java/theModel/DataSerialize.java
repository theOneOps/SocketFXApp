package theModel;

import theModel.JobClasses.Employee;
import theModel.JobClasses.Enterprise;

import java.io.*;
import java.util.HashMap;

public class DataSerialize {

    private HashMap<String, Enterprise> allEnterprises = new HashMap<>();

    public void saveData() throws IOException {
        // save all enterprises
        FileOutputStream fileOut = new FileOutputStream("data.json");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.allEnterprises);
        System.out.println("Data saved to file data.json");
        out.close();
    }

    public void loadData() throws IOException, ClassNotFoundException {
        // load all enterprises
        FileInputStream fileIn = new FileInputStream("data.json");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.allEnterprises = (HashMap<String, Enterprise>) in.readObject();
        System.out.println("Data loaded from file");
        //System.out.println(this);
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

    public void addNewEnterprise(String name, String passwd) throws IOException {
        // add new enterprise
        if (!this.allEnterprises.containsKey(name))
        {
            this.allEnterprises.put(name, new Enterprise(name, passwd));
            saveData();
        }
    }

    public void modifyEmpName(String ent, String uuid, String newName) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEmpName(newName);
        saveData();
    }

    public void modifyEmpPrename(String ent, String uuid, String newPrename) throws IOException {
        this.allEnterprises.get(ent).getEmployees().get(uuid).setEmpPrename(newPrename);
        saveData();
    }

    @Override
    public String toString() {
        return "DataSerialize{" + "allEnterprises=" + allEnterprises + '}';
    }
}
