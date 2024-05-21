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
    HashMap<LocalDate, ArrayList<LocalTime>> pointing = new HashMap<LocalDate, ArrayList<LocalTime>>();

    public WorkHour()
    {

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

    public void setPointing(HashMap<LocalDate, ArrayList<LocalTime>> pointing) {
        this.pointing = pointing;
    }

    public ArrayList<LocalTime> getWorkHour(LocalDate d){ return pointing.get(d);}

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
