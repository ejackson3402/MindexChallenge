package com.mindex.challenge.data;

/**
 * Data class for a reporting structure. Contains an employee and their number of direct reports.
 * @author Emily Jackson
 */
public class ReportingStructure {
    //the employee to make a reporting structure for
    private Employee employee;
    //the employee's number of direct reports
    private int numberOfReports;

    public ReportingStructure(){

    }

    public ReportingStructure(Employee employee, int numberOfReports){
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    //Getter for numberOfReports
    public int getNumberOfReports() {
        return numberOfReports;
    }

    //Setter for numberOfReports
    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    //Getter for employee
    public Employee getEmployee() {
        return employee;
    }

    //Setter for employee
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
