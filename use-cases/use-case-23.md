# USE CASE: 23 Produce a Report on the population of people, people living in cities, and people not living in cities in each continent.

## CHARACTERISTIC INFORMATION

### Goal in Context

As an *employee* I want *to produce a report on all the population of people living in cities and the population of people not livng in cities in each continent* so that *I can see the population of people living in cities and people not living in cities in each continent.*

### Scope

Company.

### Level

Primary task.

### Preconditions

Database contains current country populations.

### Success End Condition

A report is available for employee to view population and cities in a continent.

### Failed End Condition

No report is produced.

### Primary Actor

Employee

### Trigger

A request for finance information is sent to HR.

## MAIN SUCCESS SCENARIO

1. Information for populations, ordered by population, ascending, is requested.
2. Database creates a report ordering all populations in a continent by population and city status.
3. Employee provides report requested.

## EXTENSIONS

None.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0