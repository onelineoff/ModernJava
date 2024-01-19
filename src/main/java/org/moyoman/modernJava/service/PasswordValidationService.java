package org.moyoman.modernJava.service;

import org.moyoman.modernJava.auth.PasswordValidityEnum;

public interface PasswordValidationService {
	// TODO These should be autowired, or read from a database.
	public static final Integer MIN_VALID_PASSWORD = 5;
	public static final Integer MAX_VALID_PASSWORD = 12;

	public PasswordValidityEnum validatePassword(String password);
}
