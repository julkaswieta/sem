# USE CASE: 7 Update an employee's details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *update an employee's details* so that *the employee's details are kept up-to-date.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the employee.  Database contains incomplete/obsolete details of the employee.

### Success End Condition

An employee's details are updated.

### Failed End Condition

An employee's details are not (fully) updated.

### Primary Actor

HR Advisor.

### Trigger

An employee's details change.

## MAIN SUCCESS SCENARIO

1. An employee's details change.
2. HR advisor captures the name and details of the employee to update.
3. HR advisor updates the details.

## EXTENSIONS

2. **Employee is not in the system**:
    1. HR advisor adds the employee.
3. **Wrong employee's details are changed**:
    1. HR advisor restores the details.
    2. HR advisor updated details of the correct employee.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
