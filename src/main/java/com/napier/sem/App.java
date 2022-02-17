package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Extract employee salary information
        ArrayList<Employee> employees = a.getAllSalaries();

        // Test the size of the returned data - should be 240124
        System.out.println(employees.size());

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Creates an Employee object with the id specified
     * @param ID ID of the employee
     * @return created Employee object
     */
    public Employee getEmployee(int ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();    // to get the manager
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, first_name, last_name, title, salaries.salary, departments.dept_name"
                            + " FROM employees JOIN titles ON (titles.emp_no = employees.emp_no)"
                            + "JOIN salaries ON (salaries.emp_no = employees.emp_no)"
                            + "JOIN dept_emp ON (dept_emp.emp_no = employees.emp_no)"
                            + "JOIN departments ON (departments.dept_no = dept_emp.dept_no)"
                            + "JOIN dept_manager ON (dept_manager.dept_no = departments.dept_no)"
                            + "WHERE employees.emp_no = " + ID
                            + " AND titles.to_date = '9999-01-01'"
                            + "AND salaries.to_date = '9999-01-01'"
                            + "GROUP BY employees.emp_no, first_name, last_name, title, salaries.salary, departments.dept_name";

            String strSelect2 =
                    "SELECT first_name, last_name "
                            + "FROM employees JOIN dept_manager ON employees.emp_no = dept_manager.emp_no "
                            + "JOIN departments ON dept_manager.dept_no = departments.dept_no "
                            + "JOIN dept_emp ON employees.emp_no = dept_emp.emp_no "
                            + "WHERE departments.dept_no = (SELECT dept_emp.dept_no "
                                    + "FROM dept_emp JOIN employees e on dept_emp.emp_no = e.emp_no "
                                    + "WHERE dept_emp.emp_no = 255530)"
                            + " AND dept_manager.to_date = '9999-01-01'";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            ResultSet rset2 = stmt2.executeQuery(strSelect2);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next() && rset2.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.title = rset.getString("title");
                emp.salary = rset.getInt("salary");
                emp.dept_name = rset.getString("dept_name");
                emp.manager = rset2.getString("first_name") + " " + rset2.getString("last_name");
                return emp;
            }
            else
            {
                return null;
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    /**
     * Displays the selected employee's data to the console
     * @param emp Employee object to display
     */
    public void displayEmployee(Employee emp)
    {
        if (emp != null)
        {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }

    /**
     * Gets all current employees and salaries
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalaries()
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for the SQL statement
            String salariesSelect =
                    "SELECT e.emp_no, first_name, last_name, salary "
            + "FROM employees e JOIN salaries s ON e.emp_no = s.emp_no "
            + "WHERE s.to_date = '9999-01-01' "
            + "ORDER BY e.emp_no ASC";

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(salariesSelect);
            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<>();
            while(rset.next())
            {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                emp.salary = rset.getInt("salary");
                employees.add(emp);
            }
            return employees;

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }
}