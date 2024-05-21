package theModel;

import theModel.JobClasses.WorkHour;
import java.io.*;
import java.util.ArrayList;

public class NotSavedWorkHours {

    ArrayList<WorkHour> allWorkHours = new ArrayList<>();
    final String fileTxt = "workhours.json";
    public NotSavedWorkHours()
    {}

    public void saveData() throws IOException {
        // save all enterprises
        FileOutputStream fileOut = new FileOutputStream(fileTxt);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this.allWorkHours);
        System.out.println(String.format("data saved to file %s", fileTxt));
        out.close();
    }

    public void loadData() throws IOException, ClassNotFoundException {
        // load all workhours
        FileInputStream fileIn = new FileInputStream(fileTxt);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        this.allWorkHours = (ArrayList<WorkHour>) in.readObject();
        System.out.println(String.format("Data loaded from file %s", fileTxt));
        //System.out.println(this);
        in.close();
    }

//    public void addWorkHour(String empId, String Hour, LocalDate date)
//    {
//        // add workhour
//        WorkHour workHour = new WorkHour(empId, Hour, "", LocalDate.now());
//        allWorkHours.add(workHour);
//    }

    public void resetAllWorkHoursArray()
    {
        allWorkHours.clear();
    }
}
