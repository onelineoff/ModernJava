Modern Java
===========

# Purpose
Example code to demonstrate various features of Java 8 and above.

It also contains my solution to various coding problems that I was given on assorted job interviews.

# Requirements
1. Java 14 or later.
2. Apache Maven, version 3.6.0 or later.

# Steps to build and run Spring Boot application
1. cd to the directory with this README file.
2. Type: mvn clean compile test.
3. Type: mvn spring-boot:run
4. REST calls are at localhost:8888, or whatever port is set in application.properties.
5. Type: mvn package to create the executable jar file.
6. For the openapi3 (swagger) interface, in the browser, go to: http://localhost:8888/swagger-ui/index.html

# Purpose
The primary purpose of this project is to learn and demonstrate new features in Java from version 8 on.

A secondary purpose is to have a complete Spring Boot application which can run either from the command line, or a docker/kubernetes environment. 

It is intended that the code on the main branch is always in a good state, while work in progress might be saved to the develop branch.

Many of these problems are implemented both with and without streams.  The time to run are compared as well as the correctness of the solution.

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

## TODO
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
