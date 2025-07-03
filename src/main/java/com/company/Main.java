package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.company.model.Employee;
import com.company.service.EmployeeService;
import com.company.service.impl.EmployeeServiceImpl;
import com.company.util.CsvParser;

public class Main {

    public static void main(String[] args) throws IOException {
        String filePath = args != null && args.length > 0 ? args[0] : "src/main/resources/employee.csv";
        List<Employee> employees = CsvParser.readEmployeesFromCsvFilePath(filePath);
        EmployeeService employeeService = new EmployeeServiceImpl();
        employeeService.loadEmployeesFromCsv(employees);

        System.out.println(
            "Employee with less salary " + employeeService.getUnderPaidManagerDetails(20));
        System.out.println(
            "Employee with more salary " + employeeService.getOverPaidManagerDetails(50));
        System.out.println(
            "Employee with long reporting line" + employeeService.getEmployeeWithLongReportingLine(
                4));

    }
}