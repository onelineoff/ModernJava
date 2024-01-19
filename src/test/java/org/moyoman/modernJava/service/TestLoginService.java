package org.moyoman.modernJava.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.moyoman.modernJava.auth.PasswordValidityEnum;
import org.moyoman.modernJava.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestLoginService {
	@Autowired
	private LoginService loginService;
	
	@Test
	public void testSuccessfulLogin() {
		String token = loginService.performLogin("onelineoff@moyoman.org", LoginService.SECRET_PASSWORD).getTokenString();
		System.out.println(token);
	}
	
	@Test
	public void testUnsuccessfulLogin() {
		try {
			loginService.performLogin("onelineoff@moyoman.org", "abcdefg");
			Assert.fail();
		}
		catch(IllegalArgumentException iae) {
			// Expected, invalid password
		}
	}
	
	@Test
	public void testPasswordLength() {
		String password = null;
		Assert.assertFalse(loginService.validateLength(password));
		password="abcd";
		Assert.assertFalse(loginService.validateLength(password));
		password="abcde";
		Assert.assertTrue(loginService.validateLength(password));
		password="123456789012";
		Assert.assertTrue(loginService.validateLength(password));
		password="1234567890123";
		Assert.assertFalse(loginService.validateLength(password));
	}
	
	@Test
	public void testPasswordVariety() {
		String password = "abcd";
		Assert.assertFalse(loginService.validateVariety(password));
		password = "1234";
		Assert.assertFalse(loginService.validateVariety(password));
		password = "12CD!@#$";
		Assert.assertFalse(loginService.validateVariety(password));
		password = "a1";
		Assert.assertTrue(loginService.validateVariety(password));
	}
	
	@Test
	public void testPasswordValidCharacters() {
		String password = "abcd1234A";
		Assert.assertFalse(loginService.validCharacters(password));
		password = "abcd1234*";
		Assert.assertFalse(loginService.validCharacters(password));
		password = "abcd 1234";
		Assert.assertFalse(loginService.validCharacters(password));
		password = "abcd1234";
		Assert.assertTrue(loginService.validCharacters(password));
	}
	
	@Test
	public void testPasswordRepeatingSequence() {
		String password = "abcdef123";
		Assert.assertFalse(loginService.containsRepeatingSequence(password));
		password = "abcdef1123";
		Assert.assertTrue(loginService.containsRepeatingSequence(password));
		password = "aabcdef123";
		Assert.assertTrue(loginService.containsRepeatingSequence(password));
		password = "abcdef1233";
		Assert.assertTrue(loginService.containsRepeatingSequence(password));
		password = "abcdecdef123";
		Assert.assertTrue(loginService.containsRepeatingSequence(password));
		password = "abcdef123abcdef123";
		Assert.assertTrue(loginService.containsRepeatingSequence(password));
	}
	
	@Test
	public void testValidPassword() {
		String password = "abcd";
		Assert.assertNotEquals(loginService.validatePassword(password), PasswordValidityEnum.OK);
		password="1234567890123";
		Assert.assertNotEquals(loginService.validatePassword(password), PasswordValidityEnum.OK);
		password = "12abCD!@#$";
		Assert.assertNotEquals(loginService.validatePassword(password), PasswordValidityEnum.OK);
		password = "abcdecdef123";
		Assert.assertNotEquals(loginService.validatePassword(password), PasswordValidityEnum.OK);
		password = "abcd1234";
		Assert.assertEquals(loginService.validatePassword(password), PasswordValidityEnum.OK);
	}
}
