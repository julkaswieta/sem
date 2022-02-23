# USE CASE: 6 View an employee's details

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *HR advisor* I want *view an employee's details* so that *the employee's promotion request can be supported.*

### Scope

Company.

### Level

Primary task.

### Preconditions

We know the employee.  Database contains the employee's details.

### Success End Condition

Details of the employee are displayed for assessment.

### Failed End Condition

No details are displayed or details are incomplete.

### Primary Actor

HR Advisor.

### Trigger

An employee's promotion request.

## MAIN SUCCESS SCENARIO

1. Employee requests a promotion.
2. HR advisor captures name of the employee to get their details.
3. HR advisor extracts the employee's details.

## EXTENSIONS

3. **Details are incomplete**:
    1. HR advisor updates the details.
4. **Details are not in the DB**:
    1. HR advisor captures the details.
    2. HR advisor adds the details.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0
