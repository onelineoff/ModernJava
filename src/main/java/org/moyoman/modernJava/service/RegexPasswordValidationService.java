package org.moyoman.modernJava.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("Regex")
public class RegexPasswordValidationService implements PasswordValidationService {
	private static Pattern validPattern;
	private static Pattern lowerCasePattern;
	private static Pattern digitPattern;
	private static Pattern repeatingPattern;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegexPasswordValidationService.class);
	
	// Match any string of the appropriate length with only lowercase letters, or digits.
	static {
		// Length between two constant values, only lower case letters or digits.
		String regex = String.format("[a-z0-9]{%d,%d}", 
				MIN_VALID_PASSWORD, MAX_VALID_PASSWORD);
		validPattern = Pattern.compile(regex);
		
		// At least one lower case letter.
		lowerCasePattern = Pattern.compile("[a-z]+");
		
		// At least one digit.
		digitPattern = Pattern.compile("[0-9]+");
		
		repeatingPattern = Pattern.compile("([a-z0-9]+)\\1");
		
	}
	
	// TODO find() instead of matches() ?
	/** Verify that all characters in the password are the right type.
	 * 
	 * @param password The password to be tested
	 * @return true if all the characters are valid, or false.
	 */
	public boolean validCharacters(String password) {
		return NonNullPattern.isMatch(validPattern, password);
	}

	/** Validate that the password is of the required length.
	 * 
	 * @param password The password to be checked.
	 * @return false if the password is null, or an incorrect length, or true.
	 */
	public boolean validateLength(String password) {
		// TODO This repeats validCharacters().  Combine into one?
		return NonNullPattern.isMatch(validPattern, password);
	}

	/** Determine if the password contains the required variety of character types.
	 * 
	 * @param password The password to be checked.
	 * @return true if the required variety of characters are present, or false.
	 */
	public boolean validateVariety(String password) {
		return NonNullPattern.find(lowerCasePattern, password) && 
			NonNullPattern.find(digitPattern, password);
	}
	
	/** Determine if one or more characters immediately repeats itself.
	 * 
	 * @param password The password to be checked.
	 * @return true if there is a repeating sequence, or false.
	 */
	public boolean containsRepeatingSequence(String password) {
		return NonNullPattern.find(repeatingPattern, password);
	}
}
