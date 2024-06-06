package theModel.JobClasses;
import org.junit.Assert;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class EnterpriseTest {

    private Enterprise ent = new Enterprise("microsoft", "micro", "10000");
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

    @org.junit.Test
    public void getEntpasswd() {
        assertEquals("micro", ent.getEntpasswd());
    }

    @org.junit.Test
    public void setEntname() {
        ent.setEntname("yahoo");
        assertEquals("yahoo", ent.getEntname());
    }

    @org.junit.Test
    public void setEntpasswd() {
        ent.setEntpasswd("yah");
        assertEquals("yah", ent.getEntpasswd());

    }

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
        ArrayList<String> empNames = new ArrayList<>();
        empNames.add(String.format("%s %s (%s) ", allEmps[1].getEmpName(), allEmps[1].getEmpPrename(),
                allEmps[1].getUuid()));
        empNames.add(String.format("%s %s (%s) ", allEmps[0].getEmpName(), allEmps[0].getEmpPrename(),
                allEmps[0].getUuid()));
        assertEquals(empNames, ent.getAllEmployeesName());
    }
}