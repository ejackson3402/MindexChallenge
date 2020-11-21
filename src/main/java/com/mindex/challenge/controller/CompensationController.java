package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for compensation requests
 * @author Emily Jackson
 */
@RestController
public class CompensationController {
    //logger for debug info
    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    //reference to the compensation service
    @Autowired
    private CompensationService compensationService;

    /**
     * Create new compensation info for the given employee
     * @param compensationInfo all of the information needed to create compensation info, taken from the path as a
     *                         comma separated list in the form /compensation/id,salary,date
     * @return the created compensation info
     */
    @GetMapping("/compensation/{compensationInfo}")
    public Compensation create(@PathVariable String[] compensationInfo) {
        LOG.debug("Received compensation create request for [{}]", compensationInfo[0]);
        float salary = Float.parseFloat(compensationInfo[1]);
        return compensationService.create(compensationInfo[0], salary, compensationInfo[2]);
    }

    /**
     * Reads existing compensation info for the given employee. Path includes additional read section to
     * avoid having an ambiguous path along with create
     * @param employeeId the id of the employee to fetch compensation info for
     * @return the retrieved compensation info
     */
    @GetMapping("/compensation/read/{employeeId}")
    public Compensation read(@PathVariable String employeeId) {
        LOG.debug("Received compensation read request for id [{}]", employeeId);
        return compensationService.read(employeeId);
    }
}
