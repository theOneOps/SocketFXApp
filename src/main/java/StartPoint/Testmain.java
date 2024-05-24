package StartPoint;

import theModel.DataSerialize;

import java.io.IOException;

public class Testmain {

    public void main(String[] args) throws IOException, ClassNotFoundException {
        DataSerialize d = new DataSerialize();
        d.loadData();

        System.out.println(d);

//        d.addNewWorkHour("microsoft", "86f158fb-2d00-4ccc-ae9c-f5b0517f36b2",
//                LocalDate.now().toString(), "12:35");
    }
}
