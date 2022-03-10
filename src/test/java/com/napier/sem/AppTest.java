package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static com.napier.sem.ReportPrint.*;

public class AppTest
{
    static Employee emp;
    @BeforeAll
    static void init()
    {
        emp = new Employee();
        emp.setEmp_no(1);
        emp.setFirst_name("Kevin");
        emp.setLast_name("Chalmers");
        emp.setTitle("Engineer");
        emp.setSalary(55000);
    }
    @Test
    void printSalariesTestNull()
    {
        printSalaries(null);
    }

    @Test
    void printSalariesTestEmpty()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        printSalaries(employees);
    }

    @Test
    void printSalariesTestContainsNull()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(null);
        printSalaries(employees);
    }

    @Test
    void printSalariesBasic()
    {
        ArrayList<Employee> employees = new ArrayList<Employee>();
        employees.add(emp);
        printSalaries(employees);
    }

    @Test
    void displayEmployeeNull()
    {
        displayEmployee(null);
    }

    @Test
    void displayEmployeeEmpty()
    {
        emp = new Employee();
        displayEmployee(emp);
    }

    @Test
    void displayEmployeeBasic()
    {
        displayEmployee(emp);
    }
}