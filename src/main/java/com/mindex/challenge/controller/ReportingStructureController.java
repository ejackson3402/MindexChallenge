package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for reporting structure requests
 * @author Emily Jackson
 */
@RestController
public class ReportingStructureController {
    //logger for debug information
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class);


    //reference to the employee service
    @Autowired
    private EmployeeService employeeService;
    //reference to the reporting structure service
    @Autowired
    private ReportingService reportingService;

    /**
     * Create a new reporting structure for the given employee and return it
     * @param id the id of the employee to make the reporting structure for
     * @return the created reporting structure
     */
    @GetMapping("/reporting/{id}")
    public ReportingStructure create(@PathVariable String id) {
        LOG.debug("Received reporting structure create request for id [{}]", id);
        return reportingService.create(employeeService.read(id));
    }
}
