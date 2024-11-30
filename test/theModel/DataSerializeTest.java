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

public class DataSerializeTest {

    private DataSerialize dataSerialize;
    private final String testfile = "testfile.ser";

    @Before
    public void setUp()
    {
        dataSerialize = new DataSerialize();
        dataSerialize.setFileText(testfile);
    }

    @After
    public void setDown()
    {
        File file = new File(testfile);
        if (file.exists())
            file.delete();
    }

    @Test
    public void saveAndLoadData() throws IOException, ClassNotFoundException {

        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        //dataSerialize.addNewEnterprise("TestEnterprise", "password", "1234");

        DataSerialize loadedDataSerialize = new DataSerialize();
        loadedDataSerialize.setFileText(testfile);
        loadedDataSerialize.loadData();

        assertNotNull(loadedDataSerialize.getEntByName("TestEnterprise"));
        assertEquals("TestEnterprise", loadedDataSerialize.getEntByName("TestEnterprise").getEntname());
    }

    @Test
    public void getAllEnterprises() throws IOException {
        Enterprise enterprise = new Enterprise("TestEnterprise", "1234");
//        Enterprise enterprise = new Enterprise("TestEnterprise", "password", "1234");
//        dataSerialize.addNewEnterprise("TestEnterprise", "password", "1234");
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");

        HashMap<String, Enterprise> ents = new HashMap<>();
        ents.put(enterprise.getEntname(), enterprise);

        assertEquals(ents, dataSerialize.getAllEnterprises());
    }

    @Test
    public void getEntByName() throws IOException {
        Enterprise enterprise = new Enterprise("TestEnterprise", "1234");
//        Enterprise enterprise = new Enterprise("TestEnterprise", "password", "1234");
//        dataSerialize.addNewEnterprise("TestEnterprise", "password", "1234");
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");

        assertEquals(enterprise, dataSerialize.getEntByName("TestEnterprise"));
    }

    @Test
    public void addNewEmployeeToEnterprise()throws IOException {
        Enterprise enterprise = new Enterprise("TestEnterprise", "1234");
//        Enterprise enterprise = new Enterprise("TestEnterprise", "password", "1234");
//        dataSerialize.addNewEnterprise("TestEnterprise", "password", "1234");
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", "John", "Doe", "09:00", "17:00");

        Enterprise ent = dataSerialize.getEntByName("TestEnterprise");
        assertNotNull(ent);
        assertEquals(1, ent.getEmployees().size());
        ArrayList<String> allEmployeesName = new ArrayList<>();
        allEmployeesName.add("No employees");
        assertEquals(allEmployeesName, enterprise.getAllEmployeesName());
    }

    @Test
    public void removeEmployeeFromEnterprise() throws IOException {
//        dataSerialize.addNewEnterprise("TestEnterprise", "password", "1234");
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00",
                "");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.removeEmployeeFromEnterprise("TestEnterprise", employee);

        assertFalse(dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .containsKey(employee.getUuid()));


    }

    @Test
    public void addNewEnterprise() throws IOException {
        dataSerialize.addNewEnterprise("NewEnterprise", "5678");

        assertNotNull(dataSerialize.getEntByName("NewEnterprise"));
        assertEquals("NewEnterprise", dataSerialize.getEntByName("NewEnterprise").getEntname());

    }

    @Test
    public void modifyEmpName() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00","");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.modifyEmpName("TestEnterprise", employee.getUuid(), "Jane");

        assertEquals("Jane", dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getEmpName());

    }

    @Test
    public void modifyEmpPrename() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00","");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.modifyEmpPrename("TestEnterprise", employee.getUuid(), "Smith");

        assertEquals("Smith", dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getEmpPrename());

    }

    @Test
    public void modifyEmpStartingHour() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00","");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.modifyEmpStartingHour("TestEnterprise", employee.getUuid(), "08:00");

        assertEquals("08:00", dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getStartingHour());

    }

    @Test
    public void modifyEmpEndingHour() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00","");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.modifyEmpEndingHour("TestEnterprise", employee.getUuid(), "18:00");

        assertEquals("18:00", dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getEndingHour());

    }

    @Test
    public void modifyEmpDepartement() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00","");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        dataSerialize.modifyEmpDepartement("TestEnterprise", employee.getUuid(), "IT");

        assertEquals("IT", dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getEmDep());

    }

    @Test
    public void addNewWorkHour() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00","");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        String date = "2023-06-05";
        String time = "09:00";
        dataSerialize.addNewWorkHour("1234", employee.getUuid(), date, time);

        assertTrue(dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getWorkHour().getPointing().containsKey(LocalDate.parse(date)));
        assertTrue(dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getWorkHour().getPointing().get(LocalDate.parse(date)).contains(LocalTime.parse(time)));

    }

    @Test
    public void modifyTimeWorkHour() throws IOException {

        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00","");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);
        dataSerialize.addNewWorkHour("TestEnterprise", employee.getUuid(), LocalDate.now().toString(),
                "09:00");

        dataSerialize.modifyTimeWorkHour("1234", employee.getUuid(), LocalDate.now().toString(),
                "09:00", "10:00");

        assertEquals(LocalTime.parse("10:00"), employee.getWorkHour().getPointing().get(LocalDate.now())
                .getFirst());

    }

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
        assertEquals(LocalTime.parse("09:00"), employee.getWorkHour().getPointing().get(newDate).getFirst());
    }

    @Test
    public void removeWorkHour() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");
        Employee employee = new Employee("John", "Doe", "09:00", "17:00","");
        dataSerialize.addNewEmployeeToEnterprise("TestEnterprise", employee);

        String date = "2023-06-05";
        String time = "09:00";
        dataSerialize.addNewWorkHour("1234", employee.getUuid(), date, time);

        dataSerialize.removeWorkHour("1234", employee.getUuid(), date, time);

        assertFalse(dataSerialize.getEntByName("TestEnterprise").getEmployees()
                .get(employee.getUuid()).getWorkHour().getPointing().get(LocalDate.parse(date)).contains(LocalTime.parse(time)));

    }

    @Test
    public void getEnterpriseClassByPort() throws IOException {
        dataSerialize.addNewEnterprise("Enterprise1","1234");
        dataSerialize.addNewEnterprise("Enterprise2", "5678");

        Enterprise foundEnterprise = dataSerialize.getEnterpriseClassByPort("5678");

        assertNotNull(foundEnterprise);
        assertEquals("Enterprise2", foundEnterprise.getEntname());
    }
//
//    @Test
//    public void changeEntPassword() throws IOException {
//        dataSerialize.addNewEnterprise("TestEnterprise","1234");
//
//        //dataSerialize.changeEntPassword("TestEnterprise", "newPassword");
//
//        assertEquals("newPassword", dataSerialize.getEntByName("TestEnterprise").getEntpasswd());
//
//    }

    @Test
    public void changeEntPort() throws IOException {
        dataSerialize.addNewEnterprise("TestEnterprise", "1234");

        dataSerialize.changeEntPort("TestEnterprise", "5678");

        assertEquals("5678", dataSerialize.getEntByName("TestEnterprise").getEntPort());
    }

    @Test
    public void changeEntName() throws IOException {
        dataSerialize.addNewEnterprise("OldName", "1234");

        dataSerialize.changeEntName("OldName", "NewName");

        assertNull(dataSerialize.getEntByName("OldName"));
        assertNotNull(dataSerialize.getEntByName("NewName"));
        assertEquals("NewName", dataSerialize.getEntByName("NewName").getEntname());
    }
}