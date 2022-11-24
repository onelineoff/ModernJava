package org.moyoman.modernJava.firstInt;

import org.junit.Assert;
import org.junit.Test;
import org.moyoman.modernJava.firstInt.FindNewInteger;

public class FindNewIntegerTest {

	@Test
	public void testFindInt() {
		Integer[] arr1 = {1,2,3,4};
		Integer[] arr2 = {3, 4, 5, 7, 9};
		FindNewInteger findNewInteger = new FindNewInteger();
		int val = findNewInteger.findFirstNotInArray(arr1, 3, 6);
		Assert.assertEquals(5, val);
		val = findNewInteger.findFirstNotInArray(arr2, 3, 12);
		Assert.assertEquals(6, val);
	}

	@Test
	public void testInvalidParameters() {
		FindNewInteger findNewInteger = new FindNewInteger();
		
		try {
			Integer arr[] = null;
			findNewInteger.findFirstNotInArray(arr, 3, 6);
			Assert.fail("Null array didn't throw exception");
		}
		catch(IllegalArgumentException iae) {
			// Expected result.
		}

		try {
			Integer[] arr1 = {1,2,3,4};
			findNewInteger.findFirstNotInArray(arr1, 3, 2);
			Assert.fail("Incorrect parameters didn't throw exception");
		}
		catch(IllegalArgumentException iae) {
			// Expected result.
		}
	}
	
	@Test
	public void tempTest() {
		System.out.println(Integer.MAX_VALUE);
		
	}
}
