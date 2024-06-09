package theModel.JobClasses;
import org.junit.Assert;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EnterpriseTest {

    private Enterprise ent = new Enterprise("microsoft", "10000");
//    private Enterprise ent = new Enterprise("microsoft", "micro", "10000");
    private final Employee[] allEmps = {new Employee("ted", "mosby", "12:00", "17:30", ""),
            new Employee("barney", "stinsky", "08:30", "20:30", "")};

    @org.junit.Test
    public void getEntname() {
        assertEquals("microsoft", ent.getEntname());
    }

    @org.junit.Test
    public void getEntPort() {
        assertEquals("10000", ent.getEntPort());
    }

//    @org.junit.Test
//    public void getEntpasswd() {
//        assertEquals("micro", ent.getEntpasswd());
//    }

    @org.junit.Test
    public void setEntname() {
        ent.setEntname("yahoo");
        assertEquals("yahoo", ent.getEntname());
    }

//    @org.junit.Test
//    public void setEntpasswd() {
//        ent.setEntpasswd("yah");
//        assertEquals("yah", ent.getEntpasswd());
//
//    }

    @org.junit.Test
    public void setEntPort() {
        ent.setEntPort("8000");
        assertEquals("8000", ent.getEntPort());
    }

    @org.junit.Test
    public void addEmployee() {
        for (Employee emp: allEmps)
            ent.addEmployee(emp);

        assertEquals(2, ent.getEmployees().size());
        assertEquals(ent.getEmployees().get(allEmps[0].getUuid()).getUuid(), allEmps[0].getUuid());
        assertNotEquals(ent.getEmployees().get(allEmps[1].getUuid()).getUuid(), allEmps[0].getUuid());
    }

    @org.junit.Test
    public void removeEmployee() {
        for (Employee emp: allEmps)
            ent.addEmployee(emp);

        ent.removeEmployee(allEmps[0]);
        assertEquals(1, ent.getEmployees().size());
        assertNull(ent.getEmployees().get(allEmps[0].getUuid()));
    }

    @org.junit.Test
    public void getAllEmployeesName()
    {
        for (Employee emp: allEmps)
            ent.addEmployee(emp);

        assertEquals(allEmps[0].getUuid(), ent.getEmployees().get(allEmps[0].getUuid()).getUuid());
        assertEquals(allEmps[0].getEmpName(), ent.getEmployees().get(allEmps[0].getUuid()).getEmpName());
        assertEquals(allEmps[0].getEmpPrename(), ent.getEmployees().get(allEmps[0].getUuid()).getEmpPrename());
        assertEquals(allEmps[0].getStartingHour(), ent.getEmployees().get(allEmps[0].getUuid()).getStartingHour());
        assertEquals(allEmps[0].getEndingHour(), ent.getEmployees().get(allEmps[0].getUuid()).getEndingHour());

        assertEquals(allEmps[1].getUuid(), ent.getEmployees().get(allEmps[1].getUuid()).getUuid());
        assertEquals(allEmps[1].getEmpName(), ent.getEmployees().get(allEmps[1].getUuid()).getEmpName());
        assertEquals(allEmps[1].getEmpPrename(), ent.getEmployees().get(allEmps[1].getUuid()).getEmpPrename());
    }
}