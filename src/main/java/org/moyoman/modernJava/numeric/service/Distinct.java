package org.moyoman.modernJava.numeric.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

/** Find the number of distinct values in an array.
 *  Provide both stream and non-stream implementations for comparison.
 */
@Service
public class Distinct {
	/** Find the number of distinct elements in the array using streams.
	 * 
	 * @param arr The array of integers
	 * @return A count of the number of distinct elements.
	 */
	public long findDistinctUsingStreams(Integer[] arr) {
		return Arrays.stream(arr).distinct().count();
	}
	
	/** Find the number of distinct elements in the array using Java 7 features.
	 * 
	 * @param arr The array of integers
	 * @return A count of the number of distinct elements.
	 */
	public long findDistinctNonFP(Integer[] arr) {
		Set<Integer> elementSet = new HashSet<>();
		for (Integer i : arr) {
			elementSet.add(i);
		}
		
		return elementSet.size();
	}
}
