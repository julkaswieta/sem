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
    void testGetEmployee()
    {
        Employee emp = app.getEmployeeByID(255530);
        assertEquals(emp.getEmp_no(), 255530);
        assertEquals(emp.getFirst_name(), "Ronghao");
        assertEquals(emp.getLast_name(), "Garigliano");
    }
}