package theModel.JobClasses;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {

    private Employee theEmployee = new Employee("ted", "mosby", "08:30", "20:30", "");

    @Test
    public void setEmpName() {
        theEmployee.setEmpName("barney");
        assertEquals("barney", theEmployee.getEmpName());
    }

    @Test
    public void setEmpPrename() {
        theEmployee.setEmpPrename("stinksy");
        assertEquals("stinksy", theEmployee.getEmpPrename());
    }

    @Test
    public void getStartingHour() {
        assertEquals("08:30", theEmployee.getStartingHour());
    }

    @Test
    public void getEndingHour() {
        assertEquals("20:30", theEmployee.getEndingHour());
    }

    @Test
    public void getEmDep() {
        assertEquals("", theEmployee.getEmDep());
    }

    @Test
    public void setEmDep() {
        theEmployee.setEmDep("DI");
        assertEquals("DI", theEmployee.getEmDep());
    }

    @Test
    public void setStartingHour() {
        theEmployee.setStartingHour("07:30");
        assertEquals("07:30", theEmployee.getStartingHour());
    }

    @Test
    public void setEndingHour() {
        theEmployee.setEndingHour("21:30");
        assertEquals("21:30", theEmployee.getEndingHour());
    }
}