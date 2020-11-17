package com.github.onelineoff.fp.basic;

import java.util.function.Function;

public class LambdaExamples {

	/** Java 7 implementation of rot13 transform.
	 * 
	 * @param input The intput string.
	 * @return The transformed string.
	 */
	public String rot13(String input) {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<input.length(); i++) {
			char c = input.charAt(i);
			
			if (Character.isLowerCase(c)) {
				c = rot13(c, 'a');
			}
			else if (Character.isUpperCase(c)) {
				c = rot13(c, 'A');
			}
			sb.append(c);
		}
		
		return sb.toString();
	}
	
//	public String lambdaRot13(String input) {
//		Function<String, String> rot13Lambda = input.chars()
//	}
	
	public String transform(Function<String, String> transformFunction, String input) {
		return null;
	}
	
	protected char rot13(char c, char pivot) {
		int index = c - pivot;
		index = (index +13) % 26;
		c = (char) (pivot + index);
		return c;
	}
}
