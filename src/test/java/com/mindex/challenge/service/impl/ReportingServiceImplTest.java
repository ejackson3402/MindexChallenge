package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit tests for the reporting service and controller
 * @author Emily Jackson
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingServiceImplTest {
    //Rest Url
    private String reportingUrl;
    //new test employees to avoid being reliant on the database for testing
    private Employee testEmployee;
    private Employee grandchildEmployee;
    private Employee secondGrandchildEmployee;
    private Employee childEmployee;

    //an instance of the employee service
    @Autowired
    private EmployeeService employeeService;

    //the port to use
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Setup to be run before the tests
     */
    @Before
    public void setup() {
        reportingUrl = "http://localhost:" + port + "/reporting/{id}";

        //create the group of employees
        testEmployee = new Employee(); //don't use create to avoid two entries in db when update is used
        testEmployee.setEmployeeId(UUID.randomUUID().toString());
        grandchildEmployee = employeeService.create(new Employee());
        secondGrandchildEmployee = employeeService.create(new Employee());
        childEmployee = new Employee(); //don't use create to avoid two entries in db when update is used
        childEmployee.setEmployeeId(UUID.randomUUID().toString());

        //setup the child's direct reports
        List<Employee> childDirectReports = new ArrayList<>();
        childDirectReports.add(grandchildEmployee);
        childDirectReports.add(secondGrandchildEmployee);
        childEmployee.setDirectReports(childDirectReports);
        childEmployee = employeeService.update(childEmployee);

        //setup the parent's direct reports
        List<Employee> testEmployeeDirectReports = new ArrayList<>();
        testEmployeeDirectReports.add(childEmployee);
        testEmployee.setDirectReports(testEmployeeDirectReports);
        testEmployee = employeeService.update(testEmployee);
    }

    /**
     * Test the reporting structure create request and its values
     */
    @Test
    public void testCreate() {
        //testing values to compare results to
        int testDirectReportsCount = 3;
        String fakeId = "fakeId";
        String createExceptionString = "Invalid employeeId: " + fakeId;

        // Create checks
        ReportingStructure createdReportingStruct = restTemplate.getForEntity(reportingUrl, ReportingStructure.class,
                testEmployee.getEmployeeId()).getBody();
        Employee reportingEmployee = createdReportingStruct.getEmployee();
        assertNotNull(reportingEmployee);
        assertEmployeeEquivalence(reportingEmployee, testEmployee);
        assertEquals(createdReportingStruct.getNumberOfReports(), testDirectReportsCount);

        //Check non-existent employee creation attempt
        Exception createException = restTemplate.getForEntity(reportingUrl, Exception.class, fakeId).getBody();
        assertEquals(createException.getMessage(), createExceptionString);
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
