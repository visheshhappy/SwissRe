package com.company;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.company.exception.BusinessException;
import com.company.model.Employee;
import com.company.model.ManagerSalaryViolation;
import com.company.service.EmployeeService;
import com.company.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmployeeServiceImplTest {

    @Test
    void testLoadEmployees() {
        EmployeeService employeeService = new EmployeeServiceImpl();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("1", "Jon", "Doe", null, BigDecimal.valueOf(50000)));


        assertDoesNotThrow(() -> employeeService.loadEmployees(employeeList));
        Employee emp = employeeService.getEmployeeDetailsById("1");
        assertNotNull(emp);
        assertEquals("1", emp.getId());
        assertNull(emp.getManagerId());
    }

    @Test
    void testLoadEmployeesInvalidInput() {
        EmployeeService employeeService = new EmployeeServiceImpl();
        List<Employee> employeeList = new ArrayList<>();
        assertThrows(BusinessException.class, () -> employeeService.loadEmployees(employeeList));
    }

    @Test
    void testLoadEmployeesNullInput() {
        EmployeeService employeeService = new EmployeeServiceImpl();
        assertThrows(BusinessException.class, () -> employeeService.loadEmployees(null));
    }

    @Test
    void testUnderPaidEmplopyeesEmployeesInvalidInput() {
        EmployeeService employeeService = new EmployeeServiceImpl();
        assertThrows(BusinessException.class, () -> employeeService.getUnderPaidManagerDetails(-1));
    }

    @Test
    void testOverPaidEmplopyeesEmployeesInvalidInput() {
        EmployeeService employeeService = new EmployeeServiceImpl();
        assertThrows(BusinessException.class, () -> employeeService.getOverPaidManagerDetails(-1));
    }

    @Test
    void testUnderpaidEmployees() {
        EmployeeService employeeService = new EmployeeServiceImpl();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("1", "Jon", "Doe", null, BigDecimal.valueOf(100000)));
        employeeList.add(new Employee("2", "Tim", "David", "1", BigDecimal.valueOf(40000)));
        employeeList.add(new Employee("3", "Joe", "Root", "2", BigDecimal.valueOf(40000)));

        assertDoesNotThrow(() -> employeeService.loadEmployees(employeeList));

        List<ManagerSalaryViolation> managerSalaryViolations = employeeService.getUnderPaidManagerDetails(20);
        assertNotNull(managerSalaryViolations);
        assertEquals(1, managerSalaryViolations.size());
        assertEquals("2", managerSalaryViolations.getFirst().getEmployee().getId());
    }

    @Test
    void testOverpaidEmployees() {
        EmployeeService employeeService = new EmployeeServiceImpl();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("1", "Jon", "Doe", null, BigDecimal.valueOf(100000)));
        employeeList.add(new Employee("2", "Tim", "David", "1", BigDecimal.valueOf(80000)));
        employeeList.add(new Employee("3", "Joe", "Root", "2", BigDecimal.valueOf(40000)));

        assertDoesNotThrow(() -> employeeService.loadEmployees(employeeList));

        List<ManagerSalaryViolation> managerSalaryViolations = employeeService.getOverPaidManagerDetails(50);
        assertNotNull(managerSalaryViolations);
        assertEquals(1, managerSalaryViolations.size());
        assertEquals("2", managerSalaryViolations.getFirst().getEmployee().getId());
    }

    @Test()
    void testReportingLines() {
        EmployeeService employeeService = new EmployeeServiceImpl();
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee("1", "Jon", "Doe", null, BigDecimal.valueOf(100000)));
        employeeList.add(new Employee("2", "Tim", "David", "1", BigDecimal.valueOf(80000)));
        employeeList.add(new Employee("3", "Joe", "Root", "2", BigDecimal.valueOf(40000)));
        employeeList.add(new Employee("4", "Steve", "Smith", "3", BigDecimal.valueOf(40000)));
        employeeList.add(new Employee("5", "Kane", "Williams", "4", BigDecimal.valueOf(40000)));


        assertDoesNotThrow(() -> employeeService.loadEmployees(employeeList));

        List<Employee> employeeWithLongReportingLine = employeeService.getEmployeeWithLongReportingLine(3);
        assertNotNull(employeeWithLongReportingLine);
        assertEquals(1, employeeWithLongReportingLine.size());
        assertEquals("5", employeeWithLongReportingLine.getFirst().getId());
    }

}
