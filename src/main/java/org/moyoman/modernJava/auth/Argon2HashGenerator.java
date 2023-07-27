package org.moyoman.modernJava.auth;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.moyoman.modernJava.domain.Argon2Credentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Argon2HashGenerator {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Argon2HashGenerator.class);
	
	private SecureRandom secureRandom;
	
	public Argon2HashGenerator() {
		LOGGER.debug("Initialize SecureRandom");
		secureRandom = new SecureRandom();
		LOGGER.debug("SecureRandom initialized");
		
	}
	
	/** Generate an Argon2 hash from the login credentials.
     *  As of August, 2022, the Argon2 hashing algorithm is considered to be the best choice for hashing passwords.
     *  @see https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
	 *  @param credentials - Contains a username, password, and possibly a salt value.
	 *  @return The credentials with the hash, and possibly the salt set.
	 *  @throws IllegalArgumentException Thrown if the credentials don't contain the expected values.
	 */
	public Argon2Credentials generateHash(String username, String password) throws IllegalArgumentException {
		
    	if (StringUtils.isEmpty(password)) {
    		String warning = "Empty password passed to generateArgon2Hash()";
    		LOGGER.warn(warning);
    		throw new IllegalArgumentException(warning);
    	}
    	
    	LOGGER.trace("generateHash db1");
		String saltStr = getBase64SaltString(username);
		LOGGER.trace("generateHash db2");
		byte[] salt = base64ToByteArr(saltStr);
		LOGGER.trace("generateHash db3");
		
		Instant start = Instant.now();
		LOGGER.debug("Create byte generator");
		Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Credentials.ARGON2_TYPE)
    			.withVersion(Argon2Credentials.ARGON2_VERSION)
    			.withIterations(Argon2Credentials.ARGON2_ITERATIONS)
    			.withMemoryAsKB(Argon2Credentials.ARGON2_MEM_LIMIT)
    			.withParallelism(Argon2Credentials.ARGON2_PARALLELISM)
    			.withSalt(salt);
		
		LOGGER.trace("generateHash db4");
		Argon2BytesGenerator argon2BytesGenerator = new Argon2BytesGenerator();
		argon2BytesGenerator.init(builder.build());
		LOGGER.trace("generateHash db5");
		byte[] result = new byte[Argon2Credentials.ARGON2_OUTPUT_LENGTH];
		LOGGER.debug("Byte generator created");
		argon2BytesGenerator.generateBytes(password.getBytes(StandardCharsets.UTF_8), result, 0, result.length);
		LOGGER.trace("generateHash db6");
    	String hashedPassword = byteArrToBase64(result);
    	LOGGER.trace("generateHash db7");
    	Instant finish = Instant.now();
		Duration hashDuration = Duration.between(start, finish);
		LOGGER.info("Argon generate hash took {} msec to generate", hashDuration.toMillis());
		
		return new Argon2Credentials(username, hashedPassword, saltStr);
	}
	
	public  boolean verifyHash(Argon2Credentials credentials, String password) {
		LOGGER.trace("verifyHash db1");
		byte[] salt = base64ToByteArr(credentials.getSalt());
		LOGGER.trace("verifyHash db2");
		Instant start = Instant.now();
		Argon2Parameters.Builder builder = new Argon2Parameters.Builder(Argon2Credentials.ARGON2_TYPE)
    			.withVersion(Argon2Credentials.ARGON2_VERSION)
    			.withIterations(Argon2Credentials.ARGON2_ITERATIONS)
    			.withMemoryAsKB(Argon2Credentials.ARGON2_MEM_LIMIT)
    			.withParallelism(Argon2Credentials.ARGON2_PARALLELISM)
    			.withSalt(salt);
		
		LOGGER.trace("verifyHash db3");
		Argon2BytesGenerator argon2BytesGenerator = new Argon2BytesGenerator();
		argon2BytesGenerator.init(builder.build());
		LOGGER.trace("verifyHash db4");
		byte[] result = new byte[Argon2Credentials.ARGON2_OUTPUT_LENGTH];
		argon2BytesGenerator.generateBytes(password.getBytes(StandardCharsets.UTF_8), result, 0, result.length);
		LOGGER.trace("verifyHash db5");
    	String hashedPassword = byteArrToBase64(result);
    	LOGGER.trace("verifyHash db6");
    	Instant finish = Instant.now();
		Duration hashDuration = Duration.between(start, finish);
		LOGGER.info("Argon verify hash took {} msec to verify", hashDuration.toMillis());
		
		return hashedPassword.equals(credentials.getHashedPassword());
		
	}

	/** Generate a base 64 representation of the salt value for the given user.
	 *  TODO: Use a different salt value for each user, and store in a database or similar place.
	 *  
	 *  @param username The user for whom the salt is generated.
	 *  @return The salt value as a base 64 string.
	 * 
	 *  @throws IllegalArgumentException
	 */
	public String getBase64SaltString(String username) throws IllegalArgumentException {
		byte[] salt = new byte[Argon2Credentials.ARGON2_SALT_LENGTH];
		for (int i=0; i<salt.length; i++) {
			salt[i] = (byte) i;
		}
//		secureRandom.nextBytes(salt);		
		return byteArrToBase64(salt);
	}

	protected String byteArrToBase64(byte[] bytes) {
        if (bytes == null) {
                return null;
        }

        return Base64.getEncoder().encodeToString(bytes);
}

	protected byte[] base64ToByteArr(String str) {
        if (str == null || str.length() == 0) {
                return null;
        }

        return Base64.getDecoder().decode(str);
}
	
}

