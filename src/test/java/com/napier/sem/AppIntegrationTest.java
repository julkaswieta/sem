package com.napier.sem;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AppIntegrationTest
{
    static App app;

    @BeforeAll
    static void init()
    {
        app = new App();
        app.connect("localhost:33060", 30000);

    }

    @Test
    void testGetEmployeeByID()
    {
        Employee emp = app.getEmployeeByID(255530);
        assertEquals(emp.getEmp_no(), 255530);
        assertEquals(emp.getFirst_name(), "Ronghao");
        assertEquals(emp.getLast_name(), "Garigliano");
    }

    @Test
    void testGetEmployeeByIDNull()
    {
        Employee emp = app.getEmployeeByID(0);
        assertEquals(null, emp);
    }

    @Test
    void testGetEmployeeByName()
    {
        Employee emp = app.getEmployeeByName("Ronghao", "Garigliano");
        assertEquals(emp.getEmp_no(), 255530);
        assertEquals(emp.getFirst_name(), "Ronghao");
        assertEquals(emp.getLast_name(), "Garigliano");
    }

    @Test
    void testGetEmployeeByNameNull()
    {
        Employee emp = app.getEmployeeByName("Kevin", "Sim");
        assertEquals(null, emp);
    }

    @Test
    void testGetAllSalaries()
    {
        ArrayList<Employee> employees = app.getAllSalaries();
        assertEquals(240124, employees.size());
    }

    @Test
    void testGetSalariesByRole()
    {
        ArrayList<Employee> employees = app.getSalariesByRole("Engineer");
        assertEquals(30983, employees.size());
    }

    @Test
    void testGetSalariesByRoleWrong()
    {
        ArrayList<Employee> employees = app.getSalariesByRole("Role");
        assertTrue(employees.isEmpty());
    }

}