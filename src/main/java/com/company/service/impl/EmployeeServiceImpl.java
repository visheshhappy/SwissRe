package com.company.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

import com.company.exception.BusinessException;
import com.company.model.Employee;
import com.company.model.ManagerSalaryViolation;
import com.company.model.ManagerSalaryViolation.ViolationType;
import com.company.model.OrganizationNode;
import com.company.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

    private final Map<String, Employee> employeeMap = new HashMap<>();
    private Map<String, OrganizationNode> nodeMap = new HashMap<>();
    private OrganizationNode ceoNode = null;

    @Override
    public void loadEmployees(List<Employee> employees) {

        if(employees == null || employees.isEmpty()) {
            throw new BusinessException("Employee list is empty");
        }

        for (Employee employee : employees) {
            employeeMap.put(employee.getId(), employee);
            OrganizationNode node = new OrganizationNode(employee);
            nodeMap.put(employee.getId(), node);

            if (employee.getManagerId() == null) {
                if (ceoNode != null) {
                    throw new BusinessException("Multiple CEOs found");
                }
                ceoNode = node;
                node.setLevel(0);
            }
        }

        for (Employee employee : employees) {
            if (employee.getManagerId() != null) {
                OrganizationNode employeeNode = nodeMap.get(employee.getId());
                OrganizationNode managerNode = nodeMap.get(employee.getManagerId());
                if (managerNode == null) {
                    throw new BusinessException(
                        "Manager not found for Manager Id " + employee.getManagerId());
                }
                managerNode.addDirectReport(employeeNode);
            }

        }
    }

    @Override
    public List<ManagerSalaryViolation> getUnderPaidManagerDetails(int comparativePercentage) {
        return getManagersWithOutsideSalaryRange(comparativePercentage, false);
    }

    @Override
    public List<ManagerSalaryViolation> getOverPaidManagerDetails(int comparativePercentage) {
        return getManagersWithOutsideSalaryRange(comparativePercentage, true);
    }

    @Override
    public List<Employee> getEmployeeWithLongReportingLine(int maxReportingLine) {
        if (maxReportingLine < 0) {
            throw new BusinessException("MaxAllowedManagers cannot be negative");
        }

        return nodeMap.values().stream()
            .filter(node -> node.getEmployee().getManagerId() != null)
            .filter(node -> node.getLevel() > maxReportingLine)
            .map(OrganizationNode::getEmployee)
            .collect(Collectors.toList());
    }

    @Override
    public Employee getEmployeeDetailsById(final String id) {
        return Optional.ofNullable(employeeMap.get(id))
            .orElseThrow(() -> new BusinessException("Employee Not found"));
    }

    public List<ManagerSalaryViolation> getManagersWithOutsideSalaryRange(
        int comparativePercentage, boolean checkOverpaid) {
        List<ManagerSalaryViolation> managerDetailsWithOutsideSalaryRange = new ArrayList<>();

        if (comparativePercentage < 0) {
            throw new BusinessException("ComparativePercentage cannot be negative");
        }

        Queue<OrganizationNode> queue = new LinkedList<>();
        queue.offer(ceoNode);

        while (!queue.isEmpty()) {
            OrganizationNode node = queue.poll();
            Employee manager = node.getEmployee();
            BigDecimal managerSalary = manager.getSalary();

            BigDecimal totalSubordinateSalary = BigDecimal.ZERO;
            for(OrganizationNode reportee : node.getDirectReports()) {
                totalSubordinateSalary = totalSubordinateSalary.add(reportee.getEmployee().getSalary());
                queue.offer(reportee);
            }

            int totalReportees = node.getDirectReports().size();
            if(totalReportees == 0){
                continue;
            }
            BigDecimal averageSalaryOfReportees = totalSubordinateSalary
                .divide(new BigDecimal(totalReportees), 2, RoundingMode.HALF_UP);
            BigDecimal percentageMultiplier = BigDecimal.valueOf(1 + comparativePercentage / 100.0);
            BigDecimal thresholdSalary = averageSalaryOfReportees.multiply(percentageMultiplier);

            boolean hasIssue = checkOverpaid ?
                managerSalary.compareTo(thresholdSalary) > 0 :
                managerSalary.compareTo(thresholdSalary) < 0;

            if (hasIssue) {
                BigDecimal salaryDifference = checkOverpaid ?
                    managerSalary.subtract(thresholdSalary) :
                    thresholdSalary.subtract(managerSalary);

                BigDecimal percentage = salaryDifference
                    .divide(thresholdSalary, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));

                managerDetailsWithOutsideSalaryRange.add(new ManagerSalaryViolation(
                    manager,
                    salaryDifference,
                    percentage, checkOverpaid ? ViolationType.OVERPAID : ViolationType.UNDERPAID
                ));
            }
        }

        return managerDetailsWithOutsideSalaryRange;
    }

}
