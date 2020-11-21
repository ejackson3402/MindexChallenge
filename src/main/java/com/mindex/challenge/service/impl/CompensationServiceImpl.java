package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service for handling compensation requests. Implements the compensation service interface
 * @author Emily Jackson
 */
@Service
public class CompensationServiceImpl implements CompensationService {
    //logger for debug info
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    //a reference to the compensation database
    @Autowired
    private CompensationRepository compensationRepository;
    //a reference to the employee database
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Create a new compensation entry for the given employee to be stored in the compensation database.
     * @param employeeId the id of the employee to make compensation info for
     * @param salary the salary of the employee
     * @param effectiveDate the date this compensation info goes into effect
     * @return the created compensation structure
     * @exception throws an exception if compensation info already exists for this employee
     */
    @Override
    public Compensation create(String employeeId, float salary, String effectiveDate) {
        LOG.debug("Creating compensation for employee with id [{}]", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        Compensation existenceCheck = compensationRepository.findByEmployee(employee);
        //check if compensation info for this employee already exists
        if(existenceCheck != null){
            throw new RuntimeException("Compensation for employee with id [" + employeeId + "] already exists.");
        }
        Compensation compensation = new Compensation(employee, salary, effectiveDate);
        compensationRepository.insert(compensation);
        return compensation;
    }

    /**
     * Read existing compensation info for a given employee
     * @param employeeId the id of the employee to get compensation info for
     * @return the found compensation info
     * @exception throws an exception if compensation info for this employee is not found
     */
    @Override
    public Compensation read(String employeeId){
        LOG.debug("Reading compensation for employee with id [{}]", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        Compensation compensation = compensationRepository.findByEmployee(employee);

        //throw exception if no matching compensation info found
        if (compensation == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        return compensation;
    }
}
