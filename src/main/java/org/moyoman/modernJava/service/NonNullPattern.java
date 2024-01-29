package org.moyoman.modernJava.service;

import java.util.regex.Pattern;

/** Wrapper for the Pattern class so that the caller doesn't have to check for a null string.
 *  It's not obvious to me why Pattern can't just return false on a null value, or why
 *  the Pattern and Matcher classes must both be final.
 *  
 *  It would be simpler and more elegant to just extend the Pattern class, and have matcher(String)
 *  return false on a null input.  Oh well.
 */
class NonNullPattern {
	protected static boolean isMatch(Pattern regex, String password) {
		if (password == null) {
			return false;
		}
		else {
			return regex.matcher(password).matches();
		}
	}
}
