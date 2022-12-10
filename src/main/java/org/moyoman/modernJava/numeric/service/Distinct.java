package org.moyoman.modernJava.numeric.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** Find the number of distinct values in an array.
 *  Provide both stream and non-stream implementations for comparison.
 */
@Service
public class Distinct {
	private static final Logger LOGGER = LoggerFactory.getLogger(Distinct.class);
	
	/** Find the number of distinct elements in the array using streams.
	 * 
	 * @param arr The array of integers
	 * @return A count of the number of distinct elements.
	 */
	public long findDistinctUsingStreams(Integer[] arr) {
		long count = Arrays.stream(arr).distinct().count();
		LOGGER.info("findDistinctUsingStreams: for array of size {}, {} are distinct", arr.length, count);
		return count;
	}
	
	/** Find the number of distinct elements in the array using Java 7 features.
	 * 
	 * @param arr The array of integers
	 * @return A count of the number of distinct elements.
	 */
	public long findDistinctNonFP(Integer[] arr) {
		List<Integer> elementList = Arrays.asList(arr);
		Set<Integer> elementSet = new HashSet<>();
		elementSet.addAll(elementList);
		
		long count = elementSet.size();
		LOGGER.info("findDistinctUsingStreams: for array of size {}, {} are distinct", arr.length, count);
		return count;
	}
}
