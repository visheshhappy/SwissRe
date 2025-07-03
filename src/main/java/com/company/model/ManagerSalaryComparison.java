package com.company.model;

import java.math.BigDecimal;

//todo better name.
public final class ManagerSalaryComparison {

    Employee employee;
    BigDecimal underPaidAmount;
    BigDecimal underPaidPercentage;

    public ManagerSalaryComparison(Employee employee, BigDecimal underPaidAmount,
        BigDecimal underPaidPercentage) {
        this.employee = employee;
        this.underPaidAmount = underPaidAmount;
        this.underPaidPercentage = underPaidPercentage;
    }

    public Employee getEmployee() {
        return employee;
    }

    public BigDecimal getUnderPaidAmount() {
        return underPaidAmount;
    }

    public BigDecimal getUnderPaidPercentage() {
        return underPaidPercentage;
    }

    @Override
    public String toString() {
        return "ManagerSalaryComparison{" +
            "employee=" + employee +
            ", underPaidAmount=" + underPaidAmount +
            ", underPaidPercentage=" + underPaidPercentage +
            '}';
    }
}
