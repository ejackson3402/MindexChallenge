package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

/**
 * Interface for handling reporting structures
 * @author Emily Jackson
 */
public interface ReportingService {
    /**
     * Create a new reporting structure for a given employee
     * @param employee the employee to make the reporting structure for
     * @return the created reporting structure
     */
    ReportingStructure create(Employee employee);
}
