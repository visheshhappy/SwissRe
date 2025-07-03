package com.company.model;

import java.math.BigDecimal;

public final class Employee {

    String id;
    String firstName;
    String lastName;
    String managerId;
    BigDecimal salary;

    public Employee(String id, String firstName, String lastName, String managerId,
        BigDecimal salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.managerId = managerId;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getManagerId() {
        return managerId;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "lastName='" + lastName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", id='" + id + '\'' +
            '}';
    }
}
