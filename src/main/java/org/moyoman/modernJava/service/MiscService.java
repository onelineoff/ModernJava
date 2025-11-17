package org.moyoman.modernJava.service;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** Placeholder for various one off implementations that don't fit elsewhere.
 * 
 */
@Service
public class MiscService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MiscService.class);
	private static Pattern validPattern;
	
	static {
		String regex = "[+-]?[0-9]+[[.e][0-9]+]*";
		validPattern = Pattern.compile(regex);
	}
	
	public double StringToDouble(String str) {
		return 0.0f;
		
	}
	
//	public void StringToInt(String str) {
//		str.chars().map(Character.charAt)
//	}
	
	protected boolean isDouble(String str) {
		if (str == null) {
			return false;
		}
		
		return validPattern.matcher(str).matches();
	}
}
