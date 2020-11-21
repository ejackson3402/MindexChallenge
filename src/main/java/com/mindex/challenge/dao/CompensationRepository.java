package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.mindex.challenge.data.Employee;

/**
 * Repository for compensation info for different employees
 * @author Emily Jackson
 */
@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
    /**
     * Find the compensation info for the given employee
     * @param employee the employee to find compensation info for
     * @return the found compensation info
     */
    Compensation findByEmployee(Employee employee);
}
