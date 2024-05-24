package theModel.JobClasses;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkHour implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    HashMap<LocalDate, ArrayList<LocalTime>> pointing;

    public WorkHour()
    {
        pointing = new HashMap<>();
    }


    public void addWorkHour(LocalDate d, LocalTime h){
        if (pointing.containsKey(d))
        {
            pointing.get(d).add(h);
        }
        else
        {
            ArrayList<LocalTime> refPointing = new ArrayList<>();
            refPointing.add(h);
            pointing.put(d, refPointing);
        }
    }

    public HashMap<LocalDate, ArrayList<LocalTime>> getPointing() {
        return pointing;
    }

    public void changeLocalTime(String date, String olderHour, String newHour)
    {
        LocalDate d = LocalDate.parse(date);
        for(int i = 0; i < pointing.get(d).toArray().length;i++)
        {
            if (pointing.get(d).get(i).equals(LocalTime.parse(olderHour)))
            {
                pointing.get(d).set(i, LocalTime.parse(newHour));
                break;
            }
        }
    }

    public void removeLocalTime(String date, String Hour)
    {

        LocalDate d = LocalDate.parse(date);
        pointing.get(d).remove(LocalTime.parse(Hour));
    }

    public void setPointing(HashMap<LocalDate, ArrayList<LocalTime>> pointing) {
        this.pointing = pointing;
    }

    // todo : add for the employees view on the appManageView
    public void modifyPointing(String olderDate,String newDate, String olderHour, String newHour)
    {
        if (olderDate.equals(newDate))
        {
            changeLocalTime(olderDate, olderHour, newHour);
        }
        else
        {
            addWorkHour(LocalDate.parse(newDate), LocalTime.parse(newHour));
            removeLocalTime(olderDate, olderHour);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder res = new StringBuilder();
        for(LocalDate date : pointing.keySet())
        {
            res.append(String.format("%s %s \n", date.toString(), pointing.get(date).toString()) );
        }
        return res.toString();
    }



}
