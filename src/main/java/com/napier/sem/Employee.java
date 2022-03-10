package com.napier.sem;

/**
 * Represents an employee
 */
public class Employee
{
    /**
     * Employee number
     */
    private int emp_no;

    /**
     * Employee's first name
     */
    private String first_name;

    /**
     * Employee's last name
     */
    private String last_name;

    /**
     * Employee's job title
     */
    private String title;

    /**
     * Employee's salary
     */
    private int salary;

    /**
     * Employee's current department
     */
    private String dept_name;

    /**
     * Employee's manager
     */
    private String manager;

    /**
     * Default empty constructor
     */
    public Employee() {}

    /**
     * Returns the employee number
     * @return  employee number
     */
    public int getEmp_no() {
        return emp_no;
    }

    /**
     * Changes the employee number
     * @param emp_no    new employee number
     */
    public void setEmp_no(int emp_no) {
        this.emp_no = emp_no;
    }

    /**
     * Returns the employee's first name
     * @return  employee's first name
     */
    public String getFirst_name() {
        return first_name;
    }

    /**
     * Changes the employee's first name
     * @param first_name    new first name to set
     */
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    /**
     * Returns employee's last name
     * @return  employee's last name
     */
    public String getLast_name() {
        return last_name;
    }

    /**
     * Changes the employee's last name
     * @param last_name new last name to set
     */
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    /**
     * Returns the employee's title
     * @return  employee's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Changes the employee's title
     * @param title new title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the employee's salary
     * @return  employee's salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * Changes the employee's salary
     * @param salary    new salary to set
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    /**
     * Gets the department name the employee works in
     * @return  department name
     */
    public String getDept_name() {
        return dept_name;
    }

    /**
     * Changes the employee's department name
     * @param dept_name new department name to set
     */
    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    /**
     * Returns the employee's manager's name
     * @return  manager's name
     */
    public String getManager() {
        return manager;
    }

    /**
     * Changes the employee's manager's name
     * @param manager   new manager's name to set
     */
    public void setManager(String manager) {
        this.manager = manager;
    }
}

