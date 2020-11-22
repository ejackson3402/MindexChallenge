package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for the Compensation Service and Controller
 * @author Emily Jackson
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {

    //Rest Urls
    private String compensationUrl;
    private String compensationReadUrl;
    private String employeeIdUrl;
    //A new test employee to avoid being reliant on the database for testing
    private Employee testEmployee;

    //an instance of the employee service
    @Autowired
    private EmployeeService employeeService;

    //port to connect to
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Setup to be run before the tests
     */
    @Before
    public void setup() {
        compensationUrl = "http://localhost:" + port + "/compensation/{compensationInfo}";
        compensationReadUrl = "http://localhost:" + port + "/compensation/read/{employeeId}";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";
        testEmployee = employeeService.create(new Employee());
    }

    /**
     * Test the compensation create and read requests and confirm their values
     */
    @Test
    public void testCreateRead() {
        //testing variables to check values with
        String testEmployeeId = testEmployee.getEmployeeId();
        double testSalary = 550000.54;
        String testDate = "10-10-20";
        String info = testEmployeeId+","+testSalary+","+testDate;
        String fakeId = "fakeId";
        String createExceptionString = "Compensation for employee with id [" + testEmployeeId +"] " +
                "already exists.";
        String readExceptionString = "Invalid employeeId: " + fakeId;

        // Create checks
        Compensation createdCompensation = restTemplate.getForEntity(compensationUrl, Compensation.class, info).getBody();
        Employee employeeInDb = restTemplate.getForEntity(employeeIdUrl, Employee.class, testEmployeeId).getBody();
        Employee compensationEmployee = createdCompensation.getEmployee();
        assertNotNull(compensationEmployee);
        assertEmployeeEquivalence(employeeInDb, compensationEmployee);

        //Check duplicate creation attempt
        Exception createException = restTemplate.getForEntity(compensationUrl, Exception.class, info).getBody();
        assertEquals(createException.getMessage(), createExceptionString);

        // Read check
        Compensation readCompensation = restTemplate.getForEntity(compensationReadUrl, Compensation.class, testEmployeeId).getBody();
        assertEquals(readCompensation.getSalary(), testSalary, .05f);
        assertEquals(readCompensation.getEffectiveDate(), testDate);
        assertEmployeeEquivalence(readCompensation.getEmployee(), employeeInDb);

        //check invalid read attempt
        Exception readException = restTemplate.getForEntity(compensationReadUrl, Exception.class, fakeId).getBody();
        assertEquals(readException.getMessage(), readExceptionString);
    }

    /**
     * Assert that two Employees are equivalent
     * @param expected what the Employee should be
     * @param actual what the Employee actually is
     */
    private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getDepartment(), actual.getDepartment());
        assertEquals(expected.getPosition(), actual.getPosition());
    }
}
