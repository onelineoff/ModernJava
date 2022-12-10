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
}
