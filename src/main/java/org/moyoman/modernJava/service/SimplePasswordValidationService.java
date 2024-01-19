package org.moyoman.modernJava.service;

import org.moyoman.modernJava.auth.PasswordValidityEnum;
import org.springframework.stereotype.Service;

@Service
public class SimplePasswordValidationService implements PasswordValidationService {
	// TODO These should be autowired, or read from a database.
	public static final Integer MIN_VALID_PASSWORD = 5;
	public static final Integer MAX_VALID_PASSWORD = 12;
	
	/** Validate that the password meets the required standard.
	 * 
	 * @param password The password to be validated.
	 * @return An enum which gives the reason for failure, or success.
	 */
	public PasswordValidityEnum validatePassword(String password) {
		// Pattern matching in switch statements requires at least Java 20, so not using that.
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
	
	/** Verify that all characters in the password are the right type.
	 * 
	 * @param password The password to be tested
	 * @return true if all the characters are valid, or false.
	 */
	protected boolean validCharacters(String password) {
		boolean validCharacters = true;
		
		for (int i = 0; i < password.length(); i++) {
			char c = password.charAt(i);
			if (!Character.isDigit(c) && !Character.isLowerCase(c)) {
				validCharacters = false;
			}
		}
		
		return validCharacters;
	}

	/** Validate that the password is of the required length.
	 * 
	 * @param password The password to be checked.
	 * @return false if the password is null, or an incorrect length, or true.
	 */
	protected boolean validateLength(String password) {
		if (password != null && password.length() >= MIN_VALID_PASSWORD && 
				password.length() <= MAX_VALID_PASSWORD) {
			return true;
		}
		else {
			return false;
		}
	}

	/** Determine if the password contains the required variety of character types.
	 * 
	 * @param password The password to be checked.
	 * @return true if the required variety of characters are present, or false.
	 */
	protected boolean validateVariety(String password) {
		boolean foundLowerCase = false;
		boolean foundDigit = false;
		
		for (int i = 0; i < password.length(); i++) {
			char c = password.charAt(i);
			if (Character.isLowerCase(c)) {
				foundLowerCase = true;
			}
			else if (Character.isDigit(c)) {
				foundDigit = true;
			}
		}
		
		return foundLowerCase && foundDigit;
	}
	
	protected boolean containsRepeatingSequence(String password) {
		boolean repeatingSequenceFlag = false;
		for (int sequenceLength = 1; sequenceLength <= password.length() / 2; sequenceLength++) {
			int lastToCheck = password.length() - (2 * sequenceLength);
			for (int index = 0; index <= lastToCheck; index++) {
				String firstSubstring = password.substring(index, index + sequenceLength);
				String secondSubstring = password.substring(
						index + sequenceLength, index + (2 * sequenceLength));
				if (firstSubstring.equals(secondSubstring)) {
					repeatingSequenceFlag = true;
				}
			}
		}
		
		return repeatingSequenceFlag;
	}
}
