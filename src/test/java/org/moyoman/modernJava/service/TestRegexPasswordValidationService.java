package org.moyoman.modernJava.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.moyoman.modernJava.auth.PasswordValidityEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRegexPasswordValidationService {

	@Autowired
	private RegexPasswordValidationService simplePasswordValidationService;
	
	@Test
	public void testPasswordLength() {
		String password = null;
		Assert.assertFalse(simplePasswordValidationService.validateLength(password));
		password="abcd";
		Assert.assertFalse(simplePasswordValidationService.validateLength(password));
		password="abcde";
		Assert.assertTrue(simplePasswordValidationService.validateLength(password));
		password="123456789012";
		Assert.assertTrue(simplePasswordValidationService.validateLength(password));
		password="1234567890123";
		Assert.assertFalse(simplePasswordValidationService.validateLength(password));
	}
	
	@Test
	public void testPasswordVariety() {
		String password = "abcd";
		Assert.assertFalse(simplePasswordValidationService.validateVariety(password));
		password = "1234";
		Assert.assertFalse(simplePasswordValidationService.validateVariety(password));
		password = "12CD!@#$";
		Assert.assertFalse(simplePasswordValidationService.validateVariety(password));
		password = "a1";
		Assert.assertTrue(simplePasswordValidationService.validateVariety(password));
	}
	
	@Test
	public void testPasswordValidCharacters() {
		String password = null;
		simplePasswordValidationService.validCharacters(password);
		password = "abcd1234A";
		Assert.assertFalse(simplePasswordValidationService.validCharacters(password));
		password = "abcd1234*";
		Assert.assertFalse(simplePasswordValidationService.validCharacters(password));
		password = "abcd 1234";
		Assert.assertFalse(simplePasswordValidationService.validCharacters(password));
		password = "abcd1234";
		Assert.assertTrue(simplePasswordValidationService.validCharacters(password));
	}
	
	@Test
	public void testPasswordRepeatingSequence() {
		String password = "abcdef123";
		Assert.assertFalse(simplePasswordValidationService.containsRepeatingSequence(password));
		password = "abcdef1123";
		Assert.assertTrue(simplePasswordValidationService.containsRepeatingSequence(password));
		password = "aabcdef123";
		Assert.assertTrue(simplePasswordValidationService.containsRepeatingSequence(password));
		password = "abcdef1233";
		Assert.assertTrue(simplePasswordValidationService.containsRepeatingSequence(password));
		password = "abcdecdef123";
		Assert.assertTrue(simplePasswordValidationService.containsRepeatingSequence(password));
		password = "abcdef123abcdef123";
		Assert.assertTrue(simplePasswordValidationService.containsRepeatingSequence(password));
	}
	
	@Test
	public void testValidPassword() {
		String password = "abcd";
		Assert.assertNotEquals(simplePasswordValidationService.validatePassword(password), PasswordValidityEnum.OK);
		password="1234567890123";
		Assert.assertNotEquals(simplePasswordValidationService.validatePassword(password), PasswordValidityEnum.OK);
		password = "12abCD!@#$";
		Assert.assertNotEquals(simplePasswordValidationService.validatePassword(password), PasswordValidityEnum.OK);
		password = "abcdecdef123";
		Assert.assertNotEquals(simplePasswordValidationService.validatePassword(password), PasswordValidityEnum.OK);
		password = "abcd1234";
		Assert.assertEquals(simplePasswordValidationService.validatePassword(password), PasswordValidityEnum.OK);
	}
}
