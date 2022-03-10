package com.napier.sem;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ReportPrint {
    /**
     * Prints a list of employees and their salaries
     * @param employees The list of employees to print
     */
    public static void printSalaries(ArrayList<Employee> employees)
    {
        try {
            // Print header
            // String.format() puts the data into columns of specified sizes
            System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
            // Loop over all employees in the list
            for(Employee emp : employees)
            {
                String emp_string = String.format("%-10s %-15s %-20s %-8s", emp.getEmp_no(), emp.getFirst_name(), emp.getLast_name(),
                        emp.getSalary());
                System.out.println(emp_string);

            }
        }
        catch (NullPointerException e) {
            System.out.println("List to print is empty");
            e.printStackTrace();
        }

    }

    /**
     * Prints a list of employees and their salaries to a Markdown file
     * @param employees employees to print
     * @param filename  filename to print to
     */
    public static void printToMD(ArrayList<Employee> employees, String filename) {
        try {
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
            for(Employee emp : employees)
            {
                String emp_string = String.format("%-10s %-15s %-20s %-8s", emp.getEmp_no(), emp.getFirst_name(), emp.getLast_name(),
                        emp.getSalary());
                writer.println(emp_string);

            }
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        catch (NullPointerException e){
            System.out.println("List to print is empty");
            e.printStackTrace();
        }
    }
}
