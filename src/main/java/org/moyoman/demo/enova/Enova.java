package org.moyoman.demo.enova;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Enova {
	protected static final Integer SENTINEL = -1;
	
	public void doStuff() {
		System.out.println("Enova, do stuff");
	}
	
	public int arrayManipulation(Integer[] A) {
		if (A == null || A.length == 0) {
			return 0;
		}
		
		// Determine the count of occurrences for each element.
		
		// TODO: This should be broken into a separate method, 
		// so that a unit test can be written for it.
		Map<Integer, Integer> countMap = new HashMap<>();
		for (int i=0; i<A.length; i++) {
			int element = A[i];
			Integer count = countMap.get(element);
			if (count == null) {
				countMap.put(element, 0);
				count = 0;
			}
			
			count++;
			countMap.put(element, count);
			
		}
		
		int totalMoves = 0;
		Set<Integer> elementSet = countMap.keySet();
		for (Integer currElement : elementSet) {
			int elementCount = countMap.get(currElement);
			
			// TODO: The next line should be a separate method with unit test.
			int moves = Math.min(elementCount, Math.abs(elementCount - currElement));
			totalMoves += moves;
		}
		return totalMoves;
	}
	
	/** Find pairs of integers based on the first and last digit, and return the maximum sum.
	 * 
	 * @param A An array of integers
	 * @return The largest sum of pairs of integers, or the sentinel value.
	 */
	public Integer getMaxSharedDigits(int[] A) {
		if (A == null || A.length == 0) {
			return SENTINEL;
		}
		
		// TODO: This is straightforward for adding the top two values, but should be made
		// more general for adding top N values.
		
		// TODO: The nested if/else statements are tedious and error prone.
		// They should be broken down into methods that can be unit tested.
		Map<String, Integer> topMap = new HashMap<>();
		Map<String, Integer> bottomMap = new HashMap<>();
		// For each key, check its existence in the bottom and top maps.
		// Start with bottom map, add if not present, add new value if
		// greater than the current value, and propagate up accordingly.
		for (Integer i : A) {
			String key = getIntToStringKey(i);
			Integer bottomValue = bottomMap.get(key);
			Integer topValue = topMap.get(key);
						
			if (bottomValue == null) {
				bottomMap.put(key, i);
			}
			else {
				if (i <= bottomValue) {					
					if (topValue == null) {
						topMap.put(key, bottomValue);
						bottomMap.put(key, i);
					}
					// No else, i is too small.
				}
				if (i > bottomValue) {
					if (topValue == null) {
						topMap.put(key, i);
					}
					else if (i > topValue) {
						bottomMap.put(key, topValue);
						topMap.put(key, i);
					}
					else {
						bottomMap.put(key, i);
					}
				}
			}			
		}
		
		int sum = SENTINEL;
		// If there are not pairs, the bottom map may contain entries,
		// but there is no result unless there is also a value in the topMap.
		if (topMap.size() > 0) {
			Set<String> keys = topMap.keySet(); 
			for (String key : keys) {
				int currSum = topMap.get(key);
				currSum += bottomMap.get(key);
				
				if (currSum > sum) {
					sum = currSum;
				}
			}
		}
		return sum;
	}
	
	/** Return a string which is the first and last characters for an integer.
	 *  For example, 1099 would return the string 19.
	 *  For a value less than 10, the digit would repeat, e.g., 5 returns 55.
	 *  
	 * @param element The input parameter
	 * @return The string representation.
	 */
	protected String getIntToStringKey(int element) {
		String str = Integer.toString(element);
		char[] carr = new char[2];
		carr[0] = str.charAt(0);
		carr[1] = str.charAt(str.length() - 1);
		return new String(carr);		
	}
	
}
