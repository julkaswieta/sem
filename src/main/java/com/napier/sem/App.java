package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;
import static com.napier.sem.ReportPrint.*;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // #1 - Salary of all employees
        /*
        // Extract employee salary information
        ArrayList<Employee> employees = a.getAllSalaries();
        // Test the size of the returned data - should be 240124
        System.out.println(employees.size());
        // Display all the employees and their salaries
        a.printSalaries(employees);
         */

        //#2 - Salary in a department
        /*
        // Extract the salary data
        Department dept = a.getDepartment("Sales");
        ArrayList<Employee> deptEmployees = a.getSalariesByDepartment(dept);
        printSalaries(deptEmployees);
         */


        // #4 - Salary of a given role
        /*
        // Get salaries by role
        String role = "Engineer";
        ArrayList<Employee> employeesByRole = a.getSalariesByRole(role);
        // Print the employee details
        printSalaries(employeesByRole);
        */

        // #6 - View an employee's details
        // search by ID
        /*
        Employee emp = a.getEmployeeByID(255530);
        displayEmployee(emp);
        // search by name
        Employee emp = a.getEmployeeByName("Bedir", "Perry");
        displayEmployee(emp);
         */

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
                System.out.println("Failed to connect to database attempt " + i);
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
    public Employee getEmployeeByID(int ID)
    {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();    // to get the manager
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, first_name, last_name, title, salaries.salary, departments.dept_name, departments.dept_no "
                            + " FROM employees JOIN titles ON (titles.emp_no = employees.emp_no)"
                            + "JOIN salaries ON (salaries.emp_no = employees.emp_no)"
                            + "JOIN dept_emp ON (dept_emp.emp_no = employees.emp_no)"
                            + "JOIN departments ON (departments.dept_no = dept_emp.dept_no)"
                            + "JOIN dept_manager ON (dept_manager.dept_no = departments.dept_no)"
                            + "WHERE employees.emp_no = " + ID
                            + " AND titles.to_date = '9999-01-01'"
                            + "AND salaries.to_date = '9999-01-01'"
                            + "GROUP BY employees.emp_no, first_name, last_name, title, salaries.salary, departments.dept_name";

            // get the manager of the employee
            String strSelect2 =
                    "SELECT employees.emp_no, first_name, last_name, departments.dept_name "
                            + "FROM employees JOIN dept_manager ON employees.emp_no = dept_manager.emp_no "
                            + "JOIN departments ON dept_manager.dept_no = departments.dept_no "
                            + "JOIN dept_emp ON employees.emp_no = dept_emp.emp_no "
                            + "WHERE departments.dept_no = (SELECT dept_emp.dept_no "
                                    + "FROM dept_emp JOIN employees e on dept_emp.emp_no = e.emp_no "
                                    + "WHERE dept_emp.emp_no = " + ID + ") "
                            + " AND dept_manager.to_date = '9999-01-01'";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            ResultSet rset2 = stmt2.executeQuery(strSelect2);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next() && rset2.next())
            {
                // create the employee
                Employee emp = processEmployee(rset, rset2);
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
     * Returns the employee with specified first and last name
     * @param fname employee's first name
     * @param lname employee's last name
     * @return  Employee object
     */
    public Employee getEmployeeByName(String fname, String lname) {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();    // to get the manager
            // Create string for SQL statement
            String strSelect = "SELECT e.emp_no, first_name, last_name, title, s.salary, d.dept_name, d.dept_no " +
                    "FROM employees e JOIN titles t ON (t.emp_no = e.emp_no) " +
                    "    JOIN salaries s ON (s.emp_no = e.emp_no) " +
                    "    JOIN dept_emp de ON (de.emp_no = e.emp_no) " +
                    "    JOIN departments d ON (d.dept_no = de.dept_no) " +
                    "    JOIN dept_manager dm ON (dm.dept_no = d.dept_no) " +
                    "WHERE e.first_name = '" + fname + "' " +
                    "  AND e.last_name = '" + lname + "' " +
                    "  AND t.to_date = '9999-01-01' " +
                    "  AND s.to_date = '9999-01-01' " +
                    "GROUP BY e.emp_no, first_name, last_name, title, s.salary, d.dept_name";


            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // get the manager of the employee
            String strSelect2 = "SELECT employees.emp_no, first_name, last_name, departments.dept_name " +
                    "FROM employees JOIN dept_manager ON employees.emp_no = dept_manager.emp_no " +
                    "    JOIN departments ON dept_manager.dept_no = departments.dept_no " +
                    "    JOIN dept_emp ON employees.emp_no = dept_emp.emp_no " +
                    "WHERE departments.dept_no = (SELECT dept_emp.dept_no " +
                    "FROM dept_emp JOIN employees e on dept_emp.emp_no = e.emp_no " +
                    "JOIN titles t on e.emp_no = t.emp_no " +
                    "WHERE e.first_name = '" + fname + "' " +
                    "    AND e.last_name = '" + lname + "'" +
                    "    AND t.to_date = '9999-01-01') " +
                    "  AND dept_manager.to_date = '9999-01-01'";
            ResultSet rset2 = stmt2.executeQuery(strSelect2);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next() && rset2.next())
            {
                // create the employee
                Employee emp = processEmployee(rset, rset2);
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
     * Gets all current employees and salaries
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<Employee> getAllSalaries()
    {
        String salariesSelect = "SELECT e.emp_no, first_name, last_name, salary "
            + "FROM employees e JOIN salaries s ON e.emp_no = s.emp_no "
            + "WHERE s.to_date = '9999-01-01' "
            + "ORDER BY e.emp_no ASC";
        ArrayList<Employee> employees = processSalaryQuery(salariesSelect);
        return employees;
    }

    /**
     * Returns a list of employees performing a specified role
     * @param role  role to extract
     * @return  an ArrayList of employees of a given role
     */
    public ArrayList<Employee> getSalariesByRole(String role) {
        String salariesSelect = "SELECT employees.emp_no, first_name, last_name, salary " +
                "  FROM employees JOIN salaries ON employees.emp_no = salaries.emp_no " +
                "                 JOIN titles ON employees.emp_no = titles.emp_no " +
                "WHERE salaries.to_date = '9999-01-01' " +
                "  AND titles.to_date = '9999-01-01' " +
                "  AND titles.title = '" + role + "'" +
                " ORDER BY employees.emp_no ASC";
        ArrayList<Employee> employees = processSalaryQuery(salariesSelect);
        return employees;
    }

    /**
     * Creates a Department object
     * @param dept_name dept name to retrieve
     * @return  created Department object
     */
    public Department getDepartment(String dept_name) {
        // SQL query to get the data
        String getDeptQuery = "SELECT d.dept_no, d.dept_name, e.emp_no, e.first_name, e.last_name "
                + " FROM departments d JOIN dept_manager dm on d.dept_no = dm.dept_no "
                + "       JOIN employees e on dm.emp_no = e.emp_no "
                + " WHERE dept_name = '" + dept_name + "' "
                + "  AND dm.to_date = '9999-01-01' ";

        // extract and process the data
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(getDeptQuery);

            // Process the ResultSet
            Department dept = new Department();
            if(rset.next())
            {
                dept.setDept_no(rset.getString("dept_no"));
                dept.setDept_name(rset.getString("dept_name"));
                Employee man = new Employee();
                man.setEmp_no(rset.getInt("emp_no"));
                man.setFirst_name(rset.getString("first_name"));
                man.setLast_name(rset.getString("last_name"));
                dept.setManager(man);
            }
            return dept;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Returns a list of employees of a given department
     * @param dept  Department object
     * @return  list of Employee objects
     */
    public ArrayList<Employee> getSalariesByDepartment(Department dept) {
        String salariesByDeptQuery = "SELECT e.emp_no, e.first_name, e.last_name, s.salary " +
                "    FROM employees e JOIN salaries s ON e.emp_no = s.emp_no " +
                "        JOIN dept_emp de on e.emp_no = de.emp_no " +
                " WHERE de.dept_no = '" + dept.getDept_no() + "' " +
                "    AND s.to_date = '9999-01-01' " +
                "ORDER BY emp_no ASC;";

        ArrayList<Employee> dept_employees = processSalaryQuery(salariesByDeptQuery);
        return dept_employees;
    }

    /**
     * Processes the query passed, returning a list of employee objects extracted
     * @param query SQL statement to execute
     * @return  ArrayList of employee objects created from extracted data
     */
    private ArrayList<Employee> processSalaryQuery(String query) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();

            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(query);

            // Extract employee information
            ArrayList<Employee> employees = new ArrayList<>();
            while(rset.next())
            {
                Employee emp = new Employee();
                emp.setEmp_no(rset.getInt("emp_no"));
                emp.setFirst_name(rset.getString("first_name"));
                emp.setLast_name(rset.getString("last_name"));
                emp.setSalary(rset.getInt("salary"));
                employees.add(emp);
            }
            return employees;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }

    /**
     * Processes the result set to create an Employee object
     * @param rset  ResultSet with employee data
     * @param rset2 ResultSet with manager data
     * @return  Employee object created
     */
    private Employee processEmployee(ResultSet rset, ResultSet rset2) {
        try{
            // create the employee
            Employee emp = new Employee();
            emp.setEmp_no(rset.getInt("emp_no"));
            emp.setFirst_name(rset.getString("first_name"));
            emp.setLast_name(rset.getString("last_name"));
            emp.setTitle(rset.getString("title"));
            emp.setSalary(rset.getInt("salary"));

            // create the manager
            Employee man = new Employee();
            man.setEmp_no(rset2.getInt("emp_no"));
            man.setFirst_name(rset2.getString("first_name"));
            man.setLast_name(rset2.getString("last_name"));
            // set the manager
            emp.setManager(man);

            // create the department
            Department dept = new Department();
            dept.setDept_no(rset.getString("dept_no"));
            dept.setDept_name(rset.getString("dept_name"));
            dept.setManager(man);
            // set the department
            emp.setDept(dept);
            man.setDept(dept);
            return emp;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee data");
            return null;
        }

    }
}