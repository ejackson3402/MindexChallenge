package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

/**
 * Interface for handling compensation requests
 * @author Emily Jackson
 */
public interface CompensationService {
    /**
     * Create a new compensation entry for the given employee to be stored in the compensation database
     * @param employeeId the id of the employee to make compensation info for
     * @param salary the salary of the employee
     * @param effectiveDate the date this compensation info goes into effect
     * @return the created compensation structure
     * @exception throws an exception if compensation info already exists for this employee
     */
    Compensation create(String employeeId, float salary, String effectiveDate);

    /**
     * Read existing compensation info for a given employee
     * @param employeeId the id of the employee to get compensation info for
     * @return the found compensation info
     * @exception throws an exception if compensation info for this employee is not found
     */
    Compensation read(String employeeId);
}
