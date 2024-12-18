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

    // todo : to add to the fonctionnality of modify workhour of an employee
    public void changeLocalTime(String date, String olderHour, String newHour)
    {
        LocalDate d = LocalDate.parse(date);
        addWorkHour(d, LocalTime.parse(newHour));

        if (pointing.containsKey(d))
        {
            int idx = pointing.get(d).indexOf(LocalTime.parse(olderHour));
            if (idx != -1)
                pointing.get(d).remove(LocalTime.parse(olderHour));
        }
    }

    // todo : to add to the fonctionnality of modify date of an employee's workhour
    public void changeDateWorkHour(String olderDate, String newDate, String hour)
    {
        addWorkHour(LocalDate.parse(newDate), LocalTime.parse(hour));

        LocalDate olderDateLt = LocalDate.parse(olderDate);

        if (pointing.containsKey(olderDateLt))
        {
            int idx = pointing.get(olderDateLt).indexOf(LocalTime.parse(hour));
            if (idx != -1)
                pointing.get(olderDateLt).remove(LocalTime.parse(hour));

            if (pointing.get(olderDateLt).isEmpty())
                pointing.remove(olderDateLt);
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
