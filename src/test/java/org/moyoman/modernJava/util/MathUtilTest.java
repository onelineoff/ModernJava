package org.moyoman.modernJava.util;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class MathUtilTest {

	@Test
	public void testXor() {
		List<Long> inputList = new ArrayList<>();
		
		inputList.add(389l);
		inputList.add(877l);
		
		// Verify the correct values for various inputs.
		long xor = MathUtils.xor(inputList);
		Assert.assertEquals(744l, xor);
		
		inputList.add(3156l);
		xor = MathUtils.xor(inputList);
		Assert.assertEquals(3772l, xor);
		
		inputList.add(1111l);
		inputList.add(23l);
		inputList.add(1493l);
		inputList.add(4073l);
		xor = MathUtils.xor(inputList);
		Assert.assertEquals(192l, xor);
		
		// The set will have the same elements, but in a different order.
		// Verify that the same value is computed.
		TreeSet<Long> sortedSet = new TreeSet<>(inputList);
		xor = MathUtils.xor(sortedSet);
		Assert.assertEquals(192l, xor);
	}
	
	@Test
	public void testFactorial() {
		Assert.assertEquals(6, MathUtils.factorial(3));
		Assert.assertEquals(24, MathUtils.factorial(4));
		Assert.assertEquals(120, MathUtils.factorial(5));
		Assert.assertEquals(720, MathUtils.factorial(6));
		Assert.assertEquals(5040, MathUtils.factorial(7));
		Assert.assertEquals(40320, MathUtils.factorial(8));
		Assert.assertEquals(362880, MathUtils.factorial(9));
		Assert.assertEquals(3628800, MathUtils.factorial(10));
		Assert.assertEquals(39916800, MathUtils.factorial(11));
		Assert.assertEquals(479001600, MathUtils.factorial(12));
		try {
			Assert.assertEquals(3, MathUtils.factorial(13));
			Assert.fail("13 too large to put factorial in an int");
		} catch (IllegalArgumentException e) {
			// Expected result
		}
	}
}
