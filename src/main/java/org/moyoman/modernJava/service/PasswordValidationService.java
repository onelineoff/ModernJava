package org.moyoman.modernJava.service;

import org.moyoman.modernJava.auth.PasswordValidityEnum;

public interface PasswordValidationService {
	// TODO These should be autowired, or read from a database.
	public static final Integer MIN_VALID_PASSWORD = 5;
	public static final Integer MAX_VALID_PASSWORD = 12;

	/** Validate that the password meets the required standard.
	 * 
	 * @param password The password to be validated.
	 * @return An enum which gives the reason for failure, or success.
	 */
	default PasswordValidityEnum validatePassword(String password) {
		// Pattern matching in switch statements requires at least Java 20, so not using that.
		if (password == null) {
			return PasswordValidityEnum.BAD_LENGTH;
		}
		if (!validateLength(password)) {
			return PasswordValidityEnum.BAD_LENGTH;
		}
		else if (!validateVariety(password)) {
			return PasswordValidityEnum.LACKING_VARIETY;
		}
		else if (containsRepeatingSequence(password)) {
			return PasswordValidityEnum.REPEATING_SEQUENCE;
		}
		else if (!validCharacters(password)) {
			return PasswordValidityEnum.INVALID_CHARACTERS;
		}
		else {
			return PasswordValidityEnum.OK;
		}
	}
	
	boolean validCharacters(String password);
	boolean validateLength(String password);
	boolean validateVariety(String password);
	boolean containsRepeatingSequence(String password);
	
}
