package com.github.onelineoff.fp.basic;

import org.junit.Assert;
import org.junit.Test;

public class LambdaExamplesTest {

	@Test
	public void testRot13() {
		LambdaExamples examples = new LambdaExamples();
		String input = "abchijmnopxyz";
		String output = examples.rot13(input);
		String expected = "nopuvwzabcklm";
		Assert.assertEquals(expected, output);
	}
	
	@Test
	public void testChars() {
		String input = "aBcDeFg";
//		input.chars().filter(String::isLowerCase).
//		System.out.println(input.chars());
	}
}
