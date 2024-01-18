package org.moyoman.modernJava.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class TestArrayUtils {

		@Test
		public void testGeneratePermutations() {
			
			Integer[] arr = {1,2,3,4};
			testGeneratePermutations(arr);
			Integer[] arr2 = {1,2,3,4,5};
			testGeneratePermutations(arr2);
			String[] arr3 = {"One", "Two", "Three", "Four", "Five", "Six"};
			testGeneratePermutations(arr3);
		}
		
		private <T> void testGeneratePermutations(T[] arr) {
			Set<String> resultSet = new HashSet<>();
			ArrayUtils arrayUtils = new ArrayUtils();
			List<T[]> permList = arrayUtils.getPermutations(arr);
			for (T[] currArr : permList) {
				String result = arrayUtils.joinElements(currArr);
				resultSet.add(result);
			}
			
			int expected = MathUtils.factorial(arr.length);
			Assert.assertEquals(expected, resultSet.size());
		}
}
