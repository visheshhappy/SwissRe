package com.company;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.company.model.Employee;
import com.company.model.ManagerSalaryViolation;
import com.company.service.EmployeeService;
import com.company.service.impl.EmployeeServiceImpl;
import com.company.util.CsvParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegrationTest {

    private String filePath = "src/test/resources/employees.csv";
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() throws IOException {
        List<Employee> employees = CsvParser.readEmployeesFromCsvFilePath(filePath);
        assertNotNull(employees);
        assertEquals(20, employees.size(), "Should load exactly 20 employees from test CSV");

        employeeService = new EmployeeServiceImpl();
        employeeService.loadEmployees(employees);
    }

    @Test
    @DisplayName("Test end to end integration")
    void integrationTest() throws IOException {
        List<ManagerSalaryViolation> underPaidManagers
            = employeeService.getUnderPaidManagerDetails(20);
        assertNotNull(underPaidManagers);
        assertFalse(underPaidManagers.isEmpty());

        List<ManagerSalaryViolation> overPaidManagers = employeeService.getOverPaidManagerDetails(
            50);
        assertNotNull(overPaidManagers);
        assertFalse(overPaidManagers.isEmpty());

        List<Employee> longReportingLineEmployees
            = employeeService.getEmployeeWithLongReportingLine(4);
        assertNotNull(longReportingLineEmployees);

        assertEquals(7, longReportingLineEmployees.size());

        for (ManagerSalaryViolation manager : underPaidManagers) {
            assertNotNull(manager.getEmployee().getId());
            assertNotNull(manager.getViolationAmount());
            assertNotNull(manager.getViolationPercentage());
            assertTrue(manager.getViolationAmount().compareTo(BigDecimal.ZERO) > 0);
        }

        for (ManagerSalaryViolation manager : overPaidManagers) {
            assertNotNull(manager.getEmployee().getId());
            assertNotNull(manager.getViolationAmount());
            assertNotNull(manager.getViolationPercentage());
            assertTrue(manager.getViolationAmount().compareTo(BigDecimal.ZERO) > 0);
        }
    }

}
