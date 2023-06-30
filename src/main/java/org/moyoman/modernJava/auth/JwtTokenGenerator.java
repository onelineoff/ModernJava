package org.moyoman.modernJava.auth;

import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;

import org.moyoman.modernJava.domain.InternalRole;
import org.moyoman.modernJava.domain.InternalToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
/** Single class for processing jwt tokens and securely hashing passwords.
 * By design, this class has no other dependencies, 
 * such as secret parameters, so it can be tested in isolation.
 * 
 * TODO: Secret parameters need to be injected for production use.
 */
public class JwtTokenGenerator {
	static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenGenerator.class);
	private static final String KEY_ALGORITHM = "HmacSha512";
	private static final String SIGNATURE_ALGORITHM = "HS512";
	
	private Key secretKey = null;
	// TODO: The secret value has to be stored in a secure area, such as Vault.
	// In a microservices type environment, each service would need read access to the key
	// to verify the signature.
	/** The secret value must be 512 bits long for the HS512 algorithm. */
	private static final String SECRET_VALUE = "1234567891123456789212345678931234567894123456789512345678961234";
	private static final String ISSUER = "org.moyoman.modernJava";
	private static final String PREFIX = "Bearer ";
	private static final Integer VALID_IN_MINUTES = 10;

	@Autowired
	public JwtTokenGenerator() {
		getSecretKey();
	}

	/** Create the secret key for signing the internal jwt token.
	 * 
	 * @param keyBytes The secret key
	 * @return The generated secret key.
	 */
	public Key getSecretKey() {
		if (secretKey == null) {
			byte[] keyBytes = SECRET_VALUE.getBytes();
			secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, KEY_ALGORITHM);
		}
		
		return secretKey;
	}
	
	/** Extract the claims from the signed jwt token.
	 * 
	 * @param key The signing key.
	 * @param token The jwt token.
	 * @param issuer The issuer of the token.
	 * @return The claims extracted from the token.
	 * @throws ExpiredJwtException This is thrown if the token is no longer valid.
	 */
	public Claims getClaims(Key key, String token) throws ExpiredJwtException {
		if (token.startsWith("Bearer")) {
			token = token.substring(6);
			token = token.trim();
		}
		Claims claims = (Claims)
				Jwts.parserBuilder().setSigningKey(key).
				requireIssuer(ISSUER).build(). 
				parse(token).getBody();

		String subject = (String) claims.get("subject");
		LOGGER.info("JwtSigner: get claims for {}", subject);

		return claims;
	}
	
	// TODO The role needs to be set correctly, probably read from the database.
	// It's current implementation allows for unit tests to be easily written.
	// Those can be updated once the real implementation of this method is completed.
	/** Generate the jwt token.
	 *  The token is signed, but not encrypted.
	 *  
	 * @param subject The user for whom the token is generated.
	 * @param claimsMap The fields to be set in the claims, including entity, role, and org.
	 * @param key The secret key with which the token is signed.
	 * @return The jwt token as a string.
	 * 
	 * @throws IllegalArgumentException Thrown if the subject is not an email address.
	 */
	protected String generateJwtToken(String subject, Map<String, Object> claimsMap, Key key) throws IllegalArgumentException {
		if (! subject.contains("@")) {
			LOGGER.warn("Invalid user {}, must be an email address", subject);
			throw new IllegalArgumentException("User must be a valid email address");
		}
		
		return createJwtToken(subject, key, claimsMap);
	}
	
	/** Generate the jwt token.
	 * 
	 * @param subject The user for whom the token is generated.
	 * @param roleName One of the types of the InternalRole enum.
	 * @param key The secret key with which to sign the token.
	 * 
	 * @return The jwt token as a string.
	 * @throws IllegalArgumentException Thrown if the subject is not an email address.
	 */
	public String generateJwtToken(String subject, String roleName, Key key) throws IllegalArgumentException {
		String[] arr = subject.split("@");
		String org = arr[1];
		
		Map<String, Object> claimsMap = new HashMap<>();
		claimsMap.put("entity", subject);
		claimsMap.put("role", roleName);
		claimsMap.put("org", org);
		
		return generateJwtToken(subject, claimsMap, key);
	}
	
	/** Generate the jwt token.
	 * 
	 * @param subject The user for whom the token is generated.
	 * @param role The role of the user.
	 * @param key The secret key with which to sign the token.
	 * 
	 * @return The jwt token as a string.
	 * @throws IllegalArgumentException Thrown if the subject is not an email address.
	 */
	public String generateJwtToken(String subject, InternalRole role, Key key) throws IllegalArgumentException {
		return generateJwtToken(subject, role.name(), key);
	}
	
	/** Get a new jwt token with the same information as the old one.
	 * 
	 * @param jwtToken The valid jwt token for the user.
	 * @return A new token with the same information as the input, but with a new expired time.
	 */
	public String refreshJwtToken(String jwtToken) {
		Claims claims = getClaims(getSecretKey(), jwtToken);
		String subject = claims.getSubject();
		String org = (String) claims.get("org");
		String roleName = (String) claims.get("role");

		// Any value is fine, as long as one is set.
		InternalRole role = InternalRole.valueOf(roleName);
		return generateJwtToken(subject, role, getSecretKey());
	}
	
	/** Generate a jwt token from the input parameters.
	 * 
	 * @param subject The user for whom the token is generated.
	 * @param issuer The issuer of the token.
	 * @param key The secret key to sign the token.
	 * @param claims The claims to insert into the token.
	 * @param prefix The prefix to put before the token, e.g., Bearer
	 * @param expiryTime The expiration date of the token.
	 * @return The generated jwt token.
	 */
	protected String createJwtToken(String subject, Key key, 
			Map<String, Object> claimsMap) {
		Claims claims = Jwts.claims(claimsMap);
		Date expiryTime = Date.from(Instant.now().plus(Duration.of(VALID_IN_MINUTES, ChronoUnit.MINUTES)));
		String jwt = Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setExpiration(expiryTime)
				.setIssuer(ISSUER) 
				.signWith(key, SignatureAlgorithm.valueOf(SIGNATURE_ALGORITHM)).compact();
		jwt = PREFIX + jwt;
		if (!subject.equals("system")) {
			LOGGER.info("Generating jwt token for {} ", subject);
		}
		return jwt;
	}
	
	public InternalToken getInternalToken(String jwtToken) {
		LOGGER.debug("Get claims from jwt token");
		Claims claims = getClaims(getSecretKey(), jwtToken);
		String user = claims.getSubject();
		String roleStr = claims.get("role", String.class);
		InternalRole role = InternalRole.valueOf(roleStr);
		String org = claims.get("org", String.class);
		long expTime = claims.getExpiration().getTime();
		LOGGER.info("Got jwt token for {}, with role of {}, and expiration of {}", user, role, expTime);
		
		return new InternalToken(user, role, org, expTime, jwtToken);
	}
}
