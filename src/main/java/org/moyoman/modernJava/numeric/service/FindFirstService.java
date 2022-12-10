package org.moyoman.modernJava.numeric.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
/** Find the first integer in the specified range not present in the array of values.
 *  Another typical coding challenge problem.
 *  
 *  Both streaming, and non-FP solutions are implemented here, so they can be compared.
 *
 */
public class FindFirstService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FindFirstService.class);
	
	/** Find the first value not in the array for the specified range.
	 * 
	 * @param arr The array, which does not need to be sorted.
	 * @param first The first value to check.
	 * @param last The last value to check.
	 * @return The first value in the range which is not present in the array.
	 * @throws IllegalArgumentException If the array parameter is null, or there is no value not present in the array.
	 */
	public int findFirstNotInArrayUsingStreams(Integer[] arr, int first, int last) throws IllegalArgumentException {
		if (arr == null) {
			String msg = "Array parameter is null in call to findFirstNotInArrayUsingStreams()";
			LOGGER.warn(msg);
			throw new IllegalArgumentException(msg);
		}

		// Make sure the parameters are in order.
		int start = Math.min(first, last);
		int end = Math.max(first, last);
		
		int retValue;
		Set<Integer> intSet = new HashSet<>(Arrays.asList(arr));
		OptionalInt optionalInt =IntStream.rangeClosed(start, end).filter(i -> !intSet.contains(i)).findFirst();
		
		if (optionalInt.isPresent()) {
			retValue = optionalInt.getAsInt();
		}
		else {
			throw new IllegalArgumentException("Didn't find an int in the range not in the input array.");
		}
		return retValue;
	}
	
	public int findFirstNotInArrayNonFP(Integer[] arr, int first, int last) 
			throws IllegalArgumentException {
		if (arr == null) {
			String msg = "Array parameter is null in call to findFirstNotInArrayNonFP()";
			LOGGER.warn(msg);
			throw new IllegalArgumentException(msg);
		}

		// Make sure the parameters are in order.
		int start = Math.min(first, last);
		int end = Math.max(first, last);
		
		int missingValue = -1;
		
		Arrays.sort(arr);
		int index = 0;
		for (int i=start; i<end; i++) {
			index = indexOfValue(arr, i, index);
			if (index < 0 ) {
				missingValue = i;
				break;
			}
		}
		return missingValue;
		
	}
	
	protected int indexOfValue(Integer[] arr, int value, int index) {
		int valueIndex = -1;
		for (int i=index; i<arr.length; i++) {
			if (arr[i] == value) {
				valueIndex = i;
				break;
			}
			else if (arr[i] > value) {
				valueIndex = -1;
				break;
			}
		}
		
		return valueIndex;
	}
}
