package org.moyoman.modernJava.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.moyoman.modernJava.auth.JwtTokenGenerator;
import org.moyoman.modernJava.auth.PasswordValidityEnum;
import org.moyoman.modernJava.domain.LoginRequest;
import org.moyoman.modernJava.domain.InternalToken;
import org.moyoman.modernJava.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/v1/auth/")
/** These are the endpoints for authenticating to the system.
 *  The only methods currently supported generated an internal
 *  jwt token from a user name and password, or generate a new
 *  ticket with the same credentials from an existing one.
 *  
 *  Possible enhancements would be to integrate with other OAuth/OIDC
 *  solutions, or validating third part SAT or JWT tickets, and
 *  generating the equivalent internal jwt token.
 * 
 *  TODO: Add support for persistent storage of usernames and passwords,
 *  as well as the roles for those users.
 */
public class AuthApi {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthApi.class);
	
	@GetMapping(value="info")
	public ResponseEntity<String> getInfo(HttpServletRequest request) {
		return ResponseEntity.ok("Java Generic Cloud");
	}
	
	@Operation(summary = "User name should contain modify or admin for role, otherwise it will be read only, use hard coded password 'Very, very secret!'")
	@PostMapping(value="login", consumes="application/json", produces="application/text")
	public ResponseEntity<String> getJwtToken(HttpServletRequest request, @RequestBody LoginRequest loginRequest) {
		try {
			LOGGER.debug("Call login service for user {}", loginRequest.getUsername());
			InternalToken token = loginService.performLogin(loginRequest.getUsername(), loginRequest.getPassword());
			LOGGER.debug("Call login service completed for user {}", loginRequest.getUsername());
			return ResponseEntity.ok(token.getTokenString());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping(value="validatePassword", consumes="application/text", produces="application/text")
	@Operation(summary = "Validate that the password meets the required standards for passwords.")
	public ResponseEntity<String> validatePassword(HttpServletRequest request, @RequestBody String password) {
		LOGGER.info("Called validatePassword");
		LOGGER.info("password is {}", password);
		PasswordValidityEnum status = loginService.validatePassword(password);
		if (status == PasswordValidityEnum.OK) {
			return ResponseEntity.ok("Password meets standard requirements.");
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(status.toString());
		}
		
	}
	@Operation(summary = "A new jwt token is generated from the old, valid one with a new expire time, but otherwise unchanged.")
	@PostMapping(value="refresh", produces="application/text")
	public ResponseEntity<String> refreshJwtToken(HttpServletRequest request) {
		try {
			String jwtToken = request.getHeader("Authorization");
			if (!StringUtils.isEmpty(jwtToken)) {
				String newToken  = jwtTokenGenerator.refreshJwtToken(jwtToken);
				return ResponseEntity.ok(newToken);
			}
			else {
				LOGGER.warn("refresh jwt token failed");
				return ResponseEntity.badRequest().build();
			}
		}
		catch (Exception e) {
			LOGGER.warn("Error in refresh jwt token ", e);
			return ResponseEntity.badRequest().build();
		}
	}
}
