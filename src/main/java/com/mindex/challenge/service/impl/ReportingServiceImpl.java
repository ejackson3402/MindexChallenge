package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for handling reporting structure requests. Implements the ReportingService interface.
 * @author Emily Jackson
 */
@Service
public class ReportingServiceImpl implements ReportingService {
    //logger for debug information
    private static final Logger LOG = LoggerFactory.getLogger(ReportingServiceImpl.class);

    //the employee database
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Create a new reporting structure for a given employee
     * @param employee the employee to make the reporting structure for
     * @return the created reporting structure
     */
    @Override
    public ReportingStructure create(Employee employee) {
        LOG.debug("Creating reporting structure for employee with id [{}]", employee.getEmployeeId());

        int directReports = getDirectReportsForEmployee(employee) - 1; //- 1 to compensate for included top level
        return new ReportingStructure(employee, directReports);
    }

    /**
     * Recursive helper function to count the number of direct reports for an
     * employee. Final count will include the original employee.
     * @param employee the employee to get the number of direct reports for
     * @return the number of direct reports for the employee
     */
    private int getDirectReportsForEmployee(Employee employee){
        int count = 0;
        if (employee.getDirectReports() != null && employee.getDirectReports().size() > 0){
            for(int i = 0; i < employee.getDirectReports().size(); i++){
                String directEmployeeID = employee.getDirectReports().get(i).getEmployeeId();
                Employee directEmployee = employeeRepository.findByEmployeeId(directEmployeeID);
                count += getDirectReportsForEmployee(directEmployee);
            }
            return count + 1; //also counts top level
        }else{
            return 1;
        }
    }
}
