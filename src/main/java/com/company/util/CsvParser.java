package com.company.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.company.model.Employee;

public class CsvParser {

    public static List<Employee> readEmployeesFromCsvFilePath(String filePath) throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                Employee employee = parseEmployeeLine(line);
                if (employee != null) {
                    employees.add(employee);
                }
            }
        }

        return employees;
    }

    private static Employee parseEmployeeLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        try {
            String[] fields = line.split(",", -1);
            if (fields.length != 5) {
                throw new IllegalArgumentException("Expected 5 fields, got " + fields.length);
            }

            String id = fields[0].trim();
            String firstName = fields[1].trim();
            String lastName = fields[2].trim();
            BigDecimal salary = new BigDecimal(fields[3].trim());
            String managerId = fields[4].trim().isEmpty() ? null : fields[4].trim();

            return new Employee(id, firstName, lastName, managerId, salary);

        } catch (Exception e) {
            System.err.println("Error parsing line: " + line);
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }

}
