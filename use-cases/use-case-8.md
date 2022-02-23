# USE CASE: 8 Delete an employee's details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *delete an employee's details* so that *the company is compliant with data retention legislation.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the employee.  The former employee is no longer hired.  Database contains the employee details.

### Success End Condition

An employee's details are deleted.

### Failed End Condition

An employee's details are not (fully) deleted.

### Primary Actor

HR Advisor.

### Trigger

An employee is fired/resigns.

## MAIN SUCCESS SCENARIO

1. An employee is fired or resigns.
2. HR advisor captures name of the employee to delete.
3. HR advisor deletes all details concerning the former employee.

## EXTENSIONS

3. **Employee details are not in the system**:
    1. HR advisor informs management about it.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
