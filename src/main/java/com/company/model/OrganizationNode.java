package com.company.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationNode {

    private final Employee employee;
    private OrganizationNode manager;
    private final List<OrganizationNode> directReports = new ArrayList<>();
    private int level;
    // private BigDecimal totalReporteesSalary = BigDecimal.ZERO;

    public OrganizationNode(Employee employee) {
        this.employee = employee;
    }

    public void addDirectReport(OrganizationNode reportee) {
        directReports.add(reportee);
        reportee.manager = this;
        reportee.updateLevel(this.level + 1);
        //totalReporteesSalary = totalReporteesSalary.add(reportee.getEmployee().getSalary());
    }

    private void updateLevel(int newLevel) {
        this.level = newLevel;
        for (OrganizationNode child : directReports) {
            child.updateLevel(newLevel + 1);
        }
    }

    public Employee getEmployee() {
        return employee;
    }

    public OrganizationNode getManager() {
        return manager;
    }

    public void setManager(final OrganizationNode manager) {
        this.manager = manager;
    }

    public List<OrganizationNode> getDirectReports() {
        return directReports;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    /*public BigDecimal getTotalReporteesSalary() {
        return totalReporteesSalary;
    }*/
}
