package org.moyoman.modernJava.auth;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.moyoman.modernJava.auth.Argon2HashGenerator;
import org.moyoman.modernJava.domain.Argon2Credentials;

public class TestArgon2HashGenerator {
	private String username = "onelineoff@moyoman.org";
	private String password = "12345678abcdEFGH";
	
	@Test
	public void testHashGenerator() {
		
		
		Argon2HashGenerator argon2HashGenerator = new Argon2HashGenerator();
		Argon2Credentials credentials = argon2HashGenerator.generateHash(username, password);
		String hashedPassword = credentials.getHashedPassword();
		Assert.assertEquals(Argon2Credentials.ARGON2_BASE64_OUTPUT_LENGTH, hashedPassword.length());
	}
	
	// TODO: This test is commented out because the salt value also has a todo.
	// Once that's implemented, this test should be commented out.
//	@Test
//	public void testSaltValues() {
//		Argon2HashGenerator argon2HashGenerator = new Argon2HashGenerator();
//		Argon2Credentials credentials = argon2HashGenerator.generateHash(username, password);
//		String hashedPassword1 = credentials.getHashedPassword();
//		Assert.assertEquals(Argon2Credentials.ARGON2_BASE64_OUTPUT_LENGTH, hashedPassword1.length());
//		String salt1 = credentials.getSalt();
//		Assert.assertEquals(Argon2Credentials.ARGON2_BASE64_SALT_LENGTH, salt1.length());
//		
//		Argon2Credentials credentials2 = argon2HashGenerator.generateHash(username, password);
//		String hashedPassword2 = credentials2.getHashedPassword();
//		Assert.assertEquals(Argon2Credentials.ARGON2_BASE64_OUTPUT_LENGTH, hashedPassword2.length());
//		String salt2 = credentials2.getSalt();
//		Assert.assertEquals(Argon2Credentials.ARGON2_BASE64_SALT_LENGTH, salt2.length());
//		
//		Assert.assertNotEquals(hashedPassword1, hashedPassword2);
//		Assert.assertNotEquals(salt1, salt2);
//	}
}
