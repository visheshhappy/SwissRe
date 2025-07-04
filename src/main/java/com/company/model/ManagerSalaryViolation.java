package com.company.model;

import java.math.BigDecimal;

//todo better name.
public final class ManagerSalaryViolation {

    public enum ViolationType {
        UNDERPAID,
        OVERPAID;
    }

    private final Employee employee;
    private final BigDecimal violationAmount;
    private final BigDecimal violationPercentage;
    private final ViolationType violationType;


    public ManagerSalaryViolation(Employee employee, BigDecimal violationAmount,
        BigDecimal violationPercentage, final ViolationType violationType) {
        this.employee = employee;
        this.violationAmount = violationAmount;
        this.violationPercentage = violationPercentage;
        this.violationType = violationType;
    }

    public Employee getEmployee() {
        return employee;
    }

    public BigDecimal getViolationAmount() {
        return violationAmount;
    }

    public BigDecimal getViolationPercentage() {
        return violationPercentage;
    }

    @Override
    public String toString() {
        return "ManagerSalaryViolation{" +
            "employee=" + employee +
            ", violationAmount=" + violationAmount +
            ", violationPercentage=" + violationPercentage +
            ", violationType=" + violationType +
            '}';
    }
}
