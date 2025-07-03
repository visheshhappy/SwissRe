package com.company.service;

import java.util.List;

import com.company.model.Employee;
import com.company.model.ManagerSalaryComparison;

public interface EmployeeService {

    void loadEmployeesFromCsv(List<Employee> employees);

    List<Employee> getEmployeeWithLongReportingLine(int maxReportingLine);

    Employee getEmployeeDetailsById(String id);

    List<ManagerSalaryComparison> getUnderPaidManagerDetails(int comparativePercentage);

    List<ManagerSalaryComparison> getOverPaidManagerDetails(int comparativePercentage);
}
