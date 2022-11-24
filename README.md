Modern Java
===========

# Purpose
Example code to demonstrate various features of Java 8 and above.

# Requirements
1. Java 8 or later.
2. Apache Maven, version 3.6.0 or later.

# Steps to build and run Spring Boot application
1. cd to the directory with this README file.
2. Type: mvn clean compile test.
3. Type: mvn spring-boot:run
4. REST calls are at localhost:8888, or whatever port is set in application.properties.

# Purpose
The purpose of this project is to learn and demonstrate new features in Java from version 8 on.

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

## Functional Programming
### Basic examples

The `com.github.onelineoff.fp.basic` package contains simple examples to illustrate the basic functionality.  This generally involves implementing some basic task using Java 7 code, then rewriting in one or more steps using the Java 8 functional programming constructs, then having unit tests call all of the various methods, and verify that the output is identical.  
#### StringConcat
The idea is to turn a list of 'a', 'b', 'c', 'd' into 'a-b-c-d'.
Start with a Java 7 implementation, and progressively add FP concepts to illustrate the transition from imperative to FP style.
#### Prime
Determine which of a sequence of integers are prime.  This allows for parallel computation using stream() vs parallelstream().



# TODO

1. Add Spring for the usual reasons.
2. What are best practices for stream() versus parallelStream()?  Research and implement using Spring.
