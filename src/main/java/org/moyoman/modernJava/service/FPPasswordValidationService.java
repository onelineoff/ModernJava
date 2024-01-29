package org.moyoman.modernJava.service;

import org.springframework.stereotype.Service;

// TODO - Implement using functional programming/streaming.
@Service("FP")
public class FPPasswordValidationService implements PasswordValidationService {

	@Override
	public boolean validCharacters(String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateLength(String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean validateVariety(String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsRepeatingSequence(String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
