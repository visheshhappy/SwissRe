package com.company.service;

import java.util.List;

import com.company.model.Employee;
import com.company.model.ManagerSalaryViolation;

public interface EmployeeService {

    /**
     * Load a list of employees.
     *
     * @param employees The list of employees to be loaded.
     */
    void loadEmployees(List<Employee> employees);

    /**
     * Get list of employees with reporting line greater then maxreprotingLine
     *
     * @param maxReportingLine the max reporting line.
     * @return List of employees with reporting line greater then maxReproting line.
     */
    List<Employee> getEmployeeWithLongReportingLine(int maxReportingLine);

    /**
     * Get employees by id.
     *
     * @param id the id of employee
     * @return employee if found else null.
     */
    Employee getEmployeeDetailsById(String id);

    /**
     *
     * @param comparativePercentage
     * @return
     */
    List<ManagerSalaryViolation> getUnderPaidManagerDetails(int comparativePercentage);

    List<ManagerSalaryViolation> getOverPaidManagerDetails(int comparativePercentage);
}
