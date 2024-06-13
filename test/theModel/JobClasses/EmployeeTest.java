package theModel.JobClasses;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Test class for Employee.
 * This class contains unit tests for the Employee class.
 */
public class EmployeeTest {

    private Employee theEmployee = new Employee("ted", "mosby", "08:30", "20:30", "");

    /**
     * Tests the setEmpName method.
     * Ensures that the employee's name is correctly set.
     */
    @Test
    public void setEmpName() {
        theEmployee.setEmpName("barney");
        assertEquals("barney", theEmployee.getEmpName());
    }

    /**
     * Tests the setEmpPrename method.
     * Ensures that the employee's prename is correctly set.
     */
    @Test
    public void setEmpPrename() {
        theEmployee.setEmpPrename("stinksy");
        assertEquals("stinksy", theEmployee.getEmpPrename());
    }

    /**
     * Tests the getStartingHour method.
     * Ensures that the employee's starting hour is correctly returned.
     */
    @Test
    public void getStartingHour() {
        assertEquals("08:30", theEmployee.getStartingHour());
    }

    /**
     * Tests the getEndingHour method.
     * Ensures that the employee's ending hour is correctly returned.
     */
    @Test
    public void getEndingHour() {
        assertEquals("20:30", theEmployee.getEndingHour());
    }

    /**
     * Tests the getEmDep method.
     * Ensures that the employee's department is correctly returned.
     */
    @Test
    public void getEmDep() {
        assertEquals("", theEmployee.getEmDep());
    }

    /**
     * Tests the setEmDep method.
     * Ensures that the employee's department is correctly set.
     */
    @Test
    public void setEmDep() {
        theEmployee.setEmDep("DI");
        assertEquals("DI", theEmployee.getEmDep());
    }

    /**
     * Tests the setStartingHour method.
     * Ensures that the employee's starting hour is correctly set.
     */
    @Test
    public void setStartingHour() {
        theEmployee.setStartingHour("07:30");
        assertEquals("07:30", theEmployee.getStartingHour());
    }

    /**
     * Tests the setEndingHour method.
     * Ensures that the employee's ending hour is correctly set.
     */
    @Test
    public void setEndingHour() {
        theEmployee.setEndingHour("21:30");
        assertEquals("21:30", theEmployee.getEndingHour());
    }
}
