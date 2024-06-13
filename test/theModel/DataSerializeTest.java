package theModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import theModel.JobClasses.Employee;
import theModel.JobClasses.Enterprise;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * This class contains unit tests for the DataSerialize class.
 */
public class DataSerializeTest {

    private DataSerialize dataSerialize;
    private final String testfile = "testfile.ser";

    /**
     * Sets up the test environment before each test.
     * Initializes the DataSerialize object and sets the file name for testing.
     */
    @Before
    public void setUp() {
        dataSerialize = new DataSerialize();
        dataSerialize.setFileText(testfile);
    }

    /**
     * Cleans up the test environment after each test.
     * Deletes the test file if it exists.
     */
    @After
    public void setDown() {
        File file = new File(testfile);
        if (file.exists())
            file.delete();
    }

    /**
     * Tests saving and loading data.
     * Ensures that data is correctly saved to and loaded from the file.
     */
    @Test
    public void saveAndLoadData() throws IOException, ClassNotFoundException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");

        DataSerialize loadedDataSerialize = new DataSerialize();
        loadedDataSerialize.setFileText(testfile);
        loadedDataSerialize.loadData();

        assertNotNull(loadedDataSerialize.getEntByName("TestEnterprise"));
        assertEquals("TestEnterprise", loadedDataSerialize.getEntByName("TestEnterprise").getEntname());
    }

    /**
     * Tests getting all enterprises.
     * Ensures that all enterprises are correctly retrieved.
     */
    @Test
    public void getAllEnterprises() throws IOException {
        Enterprise enterprise = new Enterprise("TestEnterprise", "1234");
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");

        HashMap<String, Enterprise> ents = new HashMap<>();
        ents.put(enterprise.getEntname(), enterprise);

        assertEquals(ents, dataSerialize.getAllEnterprises());
    }

    /**
     * Tests getting an enterprise by name.
     * Ensures that an enterprise is correctly retrieved by its name.
     */
    @Test
    public void getEntByName() throws IOException {
        Enterprise enterprise = new Enterprise("TestEnterprise", "1234");
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");

        assertEquals(enterprise, dataSerialize.getEntByName("TestEnterprise"));
    }

    /**
     * Tests adding a new employee to an enterprise.
     * Ensures that an employee is correctly added to the enterprise.
     */
    @Test
    public void addNewEmployeeToEnterprise() throws IOException {
        Enterprise enterprise = new Enterprise("TestEnterprise", "1234");
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", "John", "Doe", "09:00", "17:00");

        Enterprise ent = dataSerialize.getEntByName("TestEnterprise");
        assertNotNull(ent);
        assertEquals(1, ent.getEmployees().size());
        ArrayList<String> allEmployeesName = new ArrayList<>();
        allEmployeesName.add("No employees");
        assertEquals(allEmployeesName, enterprise.getAllEmployeesName());
    }

    /**
     * Tests removing an employee from an enterprise.
     * Ensures that an employee is correctly removed from the enterprise.
     */
    @Test
    public void removeEmployeeFromEnterprise() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00", "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.removeEmployeeFromEnterprise("TestEnterprise", employee);

        assertFalse(dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .containsKey(employee.getUuid()));
    }

    /**
     * Tests adding a new enterprise.
     * Ensures that a new enterprise is correctly added.
     */
    @Test
    public void addNewEnterprise() throws IOException {
        dataSerialize.addNewEnterprise("NewEnterprise", "5678");

        assertNotNull(dataSerialize.getEntByName("NewEnterprise"));
        assertEquals("NewEnterprise", dataSerialize.getEntByName("NewEnterprise").getEntname());
    }

    /**
     * Tests modifying the name of an employee.
     * Ensures that the employee's name is correctly modified.
     */
    @Test
    public void modifyEmpName() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00", "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.modifyEmpName("TestEnterprise", employee.getUuid(), "Jane");

        assertEquals("Jane", dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getEmpName());
    }

    /**
     * Tests modifying the prename of an employee.
     * Ensures that the employee's prename is correctly modified.
     */
    @Test
    public void modifyEmpPrename() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00", "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.modifyEmpPrename("TestEnterprise", employee.getUuid(), "Smith");

        assertEquals("Smith", dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getEmpPrename());
    }

    /**
     * Tests modifying the starting hour of an employee.
     * Ensures that the employee's starting hour is correctly modified.
     */
    @Test
    public void modifyEmpStartingHour() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00", "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.modifyEmpStartingHour("TestEnterprise", employee.getUuid(), "08:00");

        assertEquals("08:00", dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getStartingHour());
    }

    /**
     * Tests modifying the ending hour of an employee.
     * Ensures that the employee's ending hour is correctly modified.
     */
    @Test
    public void modifyEmpEndingHour() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00", "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.modifyEmpEndingHour("TestEnterprise", employee.getUuid(), "18:00");

        assertEquals("18:00", dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getEndingHour());
    }

    /**
     * Tests modifying the department of an employee.
     * Ensures that the employee's department is correctly modified.
     */
    @Test
    public void modifyEmpDepartement() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00", "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.modifyEmpDepartement("TestEnterprise", employee.getUuid(), "IT");

        assertEquals("IT", dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getEmDep());
    }

    /**
     * Tests adding a new work hour to an employee.
     * Ensures that a work hour is correctly added to the employee.
     */
    @Test
    public void addNewWorkHour() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00", "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        String date = "2023-06-05";
        String time = "09:00";
        dataSerialize.addNewWorkHour("1234", employee.getUuid(), date, time);

        assertTrue(dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getWorkHour().getPointing().containsKey(LocalDate.parse(date)));
        assertTrue(dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getWorkHour().getPointing().get(LocalDate.parse(date)).contains(LocalTime.parse(time)));
    }

    /**
     * Tests modifying the work hour time of an employee.
     * Ensures that the work hour time is correctly modified.
     */
    @Test
    public void modifyTimeWorkHour() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00", "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);
        dataSerialize.addNewWorkHour("TestEnterprise", employee.getUuid(), LocalDate.now().toString(),
                "09:00");

        dataSerialize.modifyTimeWorkHour("1234", employee.getUuid(), LocalDate.now().toString(),
                "09:00", "10:00");

        assertEquals(LocalTime.parse("10:00"), employee.getWorkHour().getPointing().get(LocalDate.now())
                .get(0));
    }

    /**
     * Tests modifying the work hour date of an employee.
     * Ensures that the work hour date is correctly modified.
     */
    @Test
    public void modifyDateWorkHour() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00", "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);
        dataSerialize.addNewWorkHour("TestEnterprise", employee.getUuid(), LocalDate.now().toString(),
                "09:00");

        LocalDate newDate = LocalDate.now().plusDays(1);
        dataSerialize.modifyDateWorkHour("1234", employee.getUuid(),
                LocalDate.now().toString(), newDate.toString(), "09:00");

        assertFalse(employee.getWorkHour().getPointing().containsKey(LocalDate.now()));
        assertTrue(employee.getWorkHour().getPointing().containsKey(newDate));
        assertEquals(LocalTime.parse("09:00"), employee.getWorkHour().getPointing().get(newDate).get(0));
    }

    /**
     * Tests removing a work hour from an employee.
     * Ensures that a work hour is correctly removed from the employee.
     */
    @Test
    public void removeWorkHour() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00", "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        String date = "2023-06-05";
        String time = "09:00";
        dataSerialize.addNewWorkHour("1234", employee.getUuid(), date, time);

        dataSerialize.removeWorkHour("1234", employee.getUuid(), date, time);

        assertFalse(dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getWorkHour().getPointing().get(LocalDate.parse(date)).contains(LocalTime.parse(time)));
    }

    /**
     * Tests getting an enterprise by port.
     * Ensures that an enterprise is correctly retrieved by its port.
     */
    @Test
    public void getEnterpriseClassByPort() throws IOException {
        dataSerialize.addNewEnterprise("Enterprise1", "1234");
        dataSerialize.addNewEnterprise("Enterprise2", "5678");

        Enterprise foundEnterprise = dataSerialize.getEnterpriseClassByPort("5678");

        assertNotNull(foundEnterprise);
        assertEquals("Enterprise2", foundEnterprise.getEntname());
    }

    /**
     * Tests changing the port of an enterprise.
     * Ensures that the enterprise's port is correctly changed.
     */
    @Test
    public void changeEntPort() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");

        dataSerialize.changeEntPort("TestEnterprise", "5678");

        assertEquals("5678", dataSerialize.getEntByName("TestEnterprise").getEntPort());
    }

    /**
     * Tests changing the name of an enterprise.
     * Ensures that the enterprise's name is correctly changed.
     */
    @Test
    public void changeEntName() throws IOException {
        dataSerialize.addNewEnterprise("OldName", "1234");

        dataSerialize.changeEntName("OldName", "NewName");

        assertNull(dataSerialize.getEntByName("OldName"));
        assertNotNull(dataSerialize.getEntByName("NewName"));
        assertEquals("NewName", dataSerialize.getEntByName("NewName").getEntname());
    }
}
