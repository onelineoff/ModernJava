Modern Java
===========

# Purpose
Example code to demonstrate various features of Java 8 and above.

It also contains my solution to various coding problems that I was given on assorted job interviews.

# Requirements
1. Java 17 or later.
2. Apache Maven, version 3.6.0 or later.

# Steps to build and run Spring Boot application
1. cd to the directory with this README file.
2. Type: mvn clean compile test.
3. Type: mvn spring-boot:run
4. REST calls are at localhost:8888, or whatever port is set in application.properties.
5. Type: mvn package to create the executable jar file.
6. For the openapi3 (swagger) interface, in the browser, go to: http://localhost:8888/swagger-ui/index.html

# Caveat
As of right now, the application won't start unless there is a Postgres database set up as in the application.properties file.  Going forward, there are a number of solutions to this, including:
* In memory database for test.
* Containerized Postgres database that runs during tests, set up by Terraform script (not yet written).
* Set up a public, read only Postgres database that any tests can use (not sure if this is feasible).

# Purpose
The primary purpose of this project is to learn and demonstrate new features in Java from version 8 on.

A secondary purpose is to have a complete Spring Boot application which can run either from the command line, or a docker/kubernetes environment. 

It is intended that the code on the main branch is always in a good state, while work in progress might be saved to the develop branch.

Many of these problems are implemented both with and without streams.  The time to run are compared as well as the correctness of the solution.

Below is a list of new features by release.  Features are put in the
first release where they are production features, not preview or incubating.

# Java 8
This was a major release.  Some of the main features are:
* Functional Programming
* Optional Class 
* Default And Static Methods In Interfaces
* Method References
* Stream API 
* New java.time package
* New java.util.Base64 class.
* Collection API Improvements
* Concurrency API Changes/Enhancements
* Java IO Improvements
* Miscellaneous Core API Improvements

# Java 9
* Module System (Project Jigsaw)
* JShell command line tool (repl)
* New ProcessHandle class for information about external processes.
* Jcomd command line tool for analyzing running programs.
* java.util.concurrent.flow class for reactive programming.
* Unified JVM logging.
* Optional.stream() method.

# Java 10
* Assorted new stream methods.
    * List, Set, Map have copyOf(Collection c) methods.  
    * Collectors has to UnmodifiableList(), Set(), or Map().
    * Optional classes have a new orElseThrow() method.
* Start of 6 month relase cycle 

# Java 11
* java.net.http package with new Http client.
* New methods on String class:
    * isBlank()
    * lines()
    * strip()
    * stripLeading()
    * stripTrailing()
    * repeat()
* Files class contains readString() and writeString() methods.
* Collection has a new toArray() method which returns an array of the correct type, not Object[].
* Predicate has a new not() method.
* Epsilon no-op garbage collector, useful under special conditions.
* JFR (Java Flight Recorder) now open sourced.
* JMC (Java Mission Control) now needs to be downloaded separately.
* TLS upgraded to 1.3 for greater security.
* Unicode 10 support.

# Java 12
No major changes in this release.
* String has new indent() and transform() methods.
* Files class has new mismatch() method.
* Collectors has a teeing() method for stream processing.

# Java 13
Various minor changes.
* Unicode 12.1 support

# Java 14
* Switch expressions
* More helpful NPE exceptions.
* JFR Event Streaming

# Java 15
* Text Blocks 
* Records for immutable classes.
* Sealed classes.

# Java 16
* 

## TODO
I could put a lot more time into this project, but it serves my purposes for now,
so I will be moving on to another, more useful project instead.

There are lots of limitations in this project, including, but not limited to:
* Controllers need to be added for all the services.
* The tests need to be enhanced.
* Tests need to be divided up into short and longer running for convenience.
* The pom file should be cleaned up.
* The docker functionality is not yet implemented.
* Spring security should be implemented.
* Absurd amount of logging on load tests, fix in test config.
## Packages 
### Prime
Determine which of a sequence of integers are prime.  This allows for comparison among using stream(), parallelstream(), or Java 7 type multi-threading.

### Numeric
This contains various coding challenges that I wrote during live coding interviews.  I have made some of the problems more challenging, as well as implementing them in multiple ways to show successively better implementations, or allowing comparisions in the efficiency of the solution.

#### Binary Gap
A binary gap within a positive integer N is any maximal sequence of consecutive zeros that is surrounded by ones at both ends in the binary representation of N.

So, 9 (1001) has a binary gap of 2, while 8 (100) is 0.

#### Distinct
Find the number of unique integers in an array.

This is obviously a pretty simple problem.

#### Find First
For an array of integers, find the minimum one in the specified range **NOT** in the array.

Also fairly simple, but some interesting results in the unit tests for sorted vs unsorted data, and streaming vs Java 7 implementations.

#### Maximum Sum
Categorize integers by their first and last digits, and find the pair in the same category which are the largest sum.

This is a little more complex than the previous problems, and allows for increasingly sophisticated implementations.

#### Minimum Moves
An array of integers should contain either 0 entries of a number, or the same number of entries as the number itself.

A move consists of either adding or removing an entry to the array.  The problem is to determine the minimum number of moves to modify the array so that it meets the criterion above.

## Documentation
The docs directory contains a number of markdown files which document various more recent java features.
