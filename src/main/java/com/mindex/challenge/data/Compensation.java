package com.mindex.challenge.data;

/**
 * Data class for holding compensation info. Contains the employee the info is for, their salary, and
 * the date the info is in effect from
 * @author Emily Jackson
 */
public class Compensation {
    //the employee this compensation info is for
    private Employee employee;
    //this employee's salary
    private float salary;
    //the date from which this compensation info is in effect
    private String effectiveDate;

    public Compensation(Employee employee, float salary, String effectiveDate){
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    //getter for employee
    public Employee getEmployee() {
        return employee;
    }

    //setter for employee
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    //getter for salary
    public float getSalary() {
        return salary;
    }

    //setter for salary
    public void setSalary(float salary) {
        this.salary = salary;
    }

    //getter for effective date
    public String getEffectiveDate() {
        return effectiveDate;
    }

    //setter for effective date
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
