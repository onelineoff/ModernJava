package org.moyoman.modernJava.service;

import org.moyoman.modernJava.auth.PasswordValidityEnum;

public interface PasswordValidationService {
	public PasswordValidityEnum validatePassword(String password);
}
