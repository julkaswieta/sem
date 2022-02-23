# USE CASE: 5 Add a new employee's details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *add a new employee's details* so that *I can ensure the new employee is paid.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the employee's data.  Database doesn't contain the new employee's data.

### Success End Condition

A new employee's data is successfully added.

### Failed End Condition

Employee data is not (fully) added. 

### Primary Actor

HR Advisor.

### Trigger

A new employee is hired.

## MAIN SUCCESS SCENARIO

1. A new employee is hired.
2. HR advisor captures details of the new employee.
3. HR advisor adds the details.

## EXTENSIONS

3. **Details are incomplete**:
    1. HR advisor asks for clarification.
4. **Employee is already added**:
    2. HR advisor asks for clarification.
    3. HR advisor updates the details.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
