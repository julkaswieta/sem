package com.napier.sem;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ReportPrint {
    /**
     * Displays the selected employee's data to the console
     * @param emp Employee object to display
     */
    public static void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.getEmp_no() + " "
                            + emp.getFirst_name() + " "
                            + emp.getLast_name() + "\n"
                            + emp.getTitle() + "\n"
                            + "Salary:" + emp.getSalary() + "\n"
                            + emp.getDeptName() + "\n"
                            + "Manager: " + emp.getManagerName() + "\n");
        }
        else {
            System.out.println("No employee to print");
        }
    }

    /**
     * Prints a list of employees and their salaries
     * @param employees The list of employees to print
     */
    public static void printSalaries(ArrayList<Employee> employees)
    {
        if(employees == null) {
            System.out.println("No employees");
            return;
        }
        else {
            // Print header
            // String.format() puts the data into columns of specified sizes
            System.out.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
            // Loop over all employees in the list
            for(Employee emp : employees)
            {
                if(emp == null) {
                    continue;
                }
                else {
                    String emp_string = String.format("%-10s %-15s %-20s %-8s", emp.getEmp_no(), emp.getFirst_name(), emp.getLast_name(),
                            emp.getSalary());
                    System.out.println(emp_string);
                }
            }
        }
    }

    /**
     * Prints a list of employees and their salaries to a Markdown file
     * @param employees employees to print
     * @param filename  filename to print to
     */
    public static void printToMD(ArrayList<Employee> employees, String filename) {
        if(employees == null) {
            System.out.println("No employees to print");
            return;
        }
        else {
            try {
                PrintWriter writer = new PrintWriter(filename, StandardCharsets.UTF_8);
                writer.println(String.format("%-10s %-15s %-20s %-8s", "Emp No", "First Name", "Last Name", "Salary"));
                for(Employee emp : employees)
                {
                    if(emp == null) {
                        continue;
                    }
                    else {
                        String emp_string = String.format("%-10s %-15s %-20s %-8s", emp.getEmp_no(),
                                emp.getFirst_name(), emp.getLast_name(), emp.getSalary());
                        writer.println(emp_string);
                    }
                }
                writer.close();
                System.out.println("Successfully wrote to the file.");
            }
            catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

    }

    /**
     * Outputs to Markdown
     * @param employees
     */
    public static void outputEmployees(ArrayList<Employee> employees, String filename) {
        // Check employees is not null
        if (employees == null) {
            System.out.println("No employees");
            return;
        }

        StringBuilder sb = new StringBuilder();
        // Print header
        sb.append("| Emp No | First Name | Last Name | Title | Salary | Department | Manager |\r\n");
        sb.append("| --- | --- | --- | --- | --- | --- | --- |\r\n");
        // Loop over all employees in the list
        for (Employee emp : employees) {
            if (emp == null) continue;
            sb.append("| " + emp.getEmp_no() + " | " +
                    emp.getFirst_name() + " | " + emp.getLast_name() + " | " +
                    emp.getTitle() + " | " + emp.getSalary() + " | "
                    + emp.getDeptName() + " | " + emp.getManagerName() + " |\r\n");
        }
        try {
            new File("./reports/").mkdir();
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./reports/" + filename)));
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
