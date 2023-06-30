package org.moyoman.modernJava.service;

import org.moyoman.modernJava.auth.Argon2HashGenerator;
import org.moyoman.modernJava.auth.JwtTokenGenerator;
import org.moyoman.modernJava.domain.Argon2Credentials;
import org.moyoman.modernJava.domain.InternalRole;
import org.moyoman.modernJava.domain.InternalToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;

@Service
/** Provide functionality to allow the user to obtain credentials for the system.
 *  Currently, only username/password login is supported, but various other
 *  options might be added in the future for interoperability with other systems.
 */
public class LoginService {
	@Autowired
	private Argon2HashGenerator argon2HashGenerator;
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

	public static final String SECRET_PASSWORD = "Very, very secret!";
	
	// TODO The real implementation would look up the user from the database,
	// get the hashed password as well as the role, verify the first, then create the jwt token with the second.
	public InternalToken performLogin(String username, String password) throws IllegalArgumentException {
		LOGGER.debug("performLogin entered for {}", username);
		Argon2Credentials credentials = argon2HashGenerator.generateHash(username, SECRET_PASSWORD);
		LOGGER.debug("performLogin first hash generated");
		boolean loginSuccessful = argon2HashGenerator.verifyHash(credentials, password);
		LOGGER.debug("performLogin second hash generated");
		
		InternalRole role = InternalRole.READ_ONLY_CLIENT;
		if (username.endsWith(InternalToken.TOP_LEVEL_DOMAIN)) {
			role = InternalRole.READ_ONLY_TLD;
			if (username.contains("modify") ) {
				role = InternalRole.MODIFY_TLD;
			}
			else if (username.contains("admin")) {
				role = InternalRole.ADMIN_TLD;
			}
		}
		else {
			if (username.contains("modify") ) {
				role = InternalRole.MODIFY_CLIENT;
			}
			else if (username.contains("admin")) {
				role = InternalRole.ADMIN_CLIENT;
			}
		}
		
		if (loginSuccessful) {
			LOGGER.info("Successful login for {}", username);
			String token = jwtTokenGenerator.generateJwtToken(username, role, jwtTokenGenerator.getSecretKey());
			Claims claims = jwtTokenGenerator.getClaims(jwtTokenGenerator.getSecretKey(), token);
			String org = claims.get("org", String.class);
			long expireTime = claims.getExpiration().getTime();
			return new InternalToken(username, role, org, expireTime , token);
		}
		else {
			LOGGER.warn("Unsuccessful login attempt by user {}", username);
			throw new IllegalArgumentException("Unsuccessful login attempt by user " + username);
		}
	
	}
}
