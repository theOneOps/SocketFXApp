package theModel.JobClasses;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for Enterprise.
 * This class contains unit tests for the Enterprise class.
 */
public class EnterpriseTest {

    // Initialize a test Enterprise object
    private Enterprise ent = new Enterprise("microsoft", "10000");

    // Array of test Employee objects
    private final Employee[] allEmps = {
            new Employee("ted", "mosby", "12:00", "17:30", ""),
            new Employee("barney", "stinsky", "08:30", "20:30", "")
    };

    /**
     * Tests the getEntname method.
     * Ensures that the enterprise name is correctly returned.
     */
    @Test
    public void getEntname() {
        assertEquals("microsoft", ent.getEntname());
    }

    /**
     * Tests the getEntPort method.
     * Ensures that the enterprise port is correctly returned.
     */
    @Test
    public void getEntPort() {
        assertEquals("10000", ent.getEntPort());
    }

    /**
     * Tests the setEntname method.
     * Ensures that the enterprise name is correctly set.
     */
    @Test
    public void setEntname() {
        ent.setEntname("yahoo");
        assertEquals("yahoo", ent.getEntname());
    }

    /**
     * Tests the setEntPort method.
     * Ensures that the enterprise port is correctly set.
     */
    @Test
    public void setEntPort() {
        ent.setEntPort("8000");
        assertEquals("8000", ent.getEntPort());
    }

    /**
     * Tests the addEmployee method.
     * Ensures that employees are correctly added to the enterprise.
     */
    @Test
    public void addEmployee() {
        for (Employee emp : allEmps)
            ent.addEmployee(emp);

        assertEquals(2, ent.getEmployees().size());
        assertEquals(ent.getEmployees().get(allEmps[0].getUuid()).getUuid(), allEmps[0].getUuid());
        assertNotEquals(ent.getEmployees().get(allEmps[1].getUuid()).getUuid(), allEmps[0].getUuid());
    }

    /**
     * Tests the removeEmployee method.
     * Ensures that employees are correctly removed from the enterprise.
     */
    @Test
    public void removeEmployee() {
        for (Employee emp : allEmps)
            ent.addEmployee(emp);

        ent.removeEmployee(allEmps[0]);
        assertEquals(1, ent.getEmployees().size());
        assertNull(ent.getEmployees().get(allEmps[0].getUuid()));
    }

    /**
     * Tests the getAllEmployeesName method.
     * Ensures that all employee names are correctly returned.
     */
    @Test
    public void getAllEmployeesName() {
        for (Employee emp : allEmps)
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
