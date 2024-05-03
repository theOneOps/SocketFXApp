package theModel;

import theModel.JobClasses.Enterprise;
import theView.pointer.Pointer;

import java.io.*;
import java.util.HashMap;

public class DataSerialize {

    private HashMap<String, Enterprise> allEnterprises = new HashMap<>();

    public void saveData() throws IOException {
        // save all enterprises
        FileOutputStream fileOut = new FileOutputStream("data.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.allEnterprises);
        System.out.println("Data saved to file data.ser");
        out.close();
    }

    public void loadData() throws IOException, ClassNotFoundException {
        // load all enterprises
        FileInputStream fileIn = new FileInputStream("data.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.allEnterprises = (HashMap<String, Enterprise>) in.readObject();
        System.out.println("Data loaded from file");
        //System.out.println(this);
        in.close();
    }

    public HashMap<String, Enterprise> getAllEnterprises() {
        return this.allEnterprises;
    }

    public void addNewEmployeeToEnterprise(String entName, String empName, String empPrename, String HourStart, String HourEnd) {
        // add employee to enterprise
        this.allEnterprises.get(entName).addEmployee(empName, empPrename, HourStart, HourEnd);
    }

    public void addNewEnterprise(String name, String passwd) {
        // add new enterprise
        if (!this.allEnterprises.containsKey(name))
            this.allEnterprises.put(name, new Enterprise(name, passwd));
        Pointer.PrintAlert("enterprise creation", String.format("enterprise %s created successfully", name));
    }

    @Override
    public String toString() {
        return "DataSerialize{" + "allEnterprises=" + allEnterprises + '}';
    }
}
