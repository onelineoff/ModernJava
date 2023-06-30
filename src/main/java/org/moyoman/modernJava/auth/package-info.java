/** This package is responsible for authorization/authentication.
 *  The idea is to do this in a generic fashion, not dependent on
 *  the functionality for any specific cloud vendor, or other environment.
 *  
 *  A custom built Spring AOP solution is used to support this functionality.
 *  There were technical reasons for NOT using Spring Security.
 *  This could be reexamined in the future.
 *  
 *  The basic design is as follows:
 *  <UL>
 *  <LI>The user obtains a jwt token, which is signed by our secret key.
 *  <LI>Each API call is checked against our internal roles.
 *  <LI>A mechanism is provided for federating with other identity tools in both directions.
 *  </UL>
 */
package org.moyoman.modernJava.auth;