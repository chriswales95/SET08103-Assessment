# USE CASE: 15 produce a report on the top N populated cities in a country where N is provided by the user.

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *employee* I want *to produce a report on the top N populated cities in a country where N is provided by the user* so that *I can share this on the company website.*

### Scope

Company.

### Level

Primary task.

### Preconditions

Database contains current country populations.

### Success End Condition

A report is available for employee to view cities.

### Failed End Condition

No report is produced.

### Primary Actor

Employee

### Trigger

A request for finance information is sent to employee.

## MAIN SUCCESS SCENARIO

1. Information for countries, ordered by population, ascending, is requested.
2. Database creates a report ordering all countries by population.
3. Employee provides report requested.

## EXTENSIONS

None.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0