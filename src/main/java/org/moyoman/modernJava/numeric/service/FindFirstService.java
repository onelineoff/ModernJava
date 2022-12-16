package org.moyoman.modernJava.numeric.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.IntStream;

import org.moyoman.modernJava.dto.FindFirstEfficiencyDto;
import org.moyoman.modernJava.dto.MsecDuration;
import org.moyoman.modernJava.util.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	private static final Integer NOT_FOUND = -1;
	
	@Autowired
	private ArrayUtils arrayUtils;
	
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
		
		int missingValue;
		Set<Integer> intSet = new HashSet<>(Arrays.asList(arr));
		OptionalInt optionalInt =IntStream.rangeClosed(start, end).filter(i -> !intSet.contains(i)).findFirst();
		
		if (optionalInt.isPresent()) {
			missingValue = optionalInt.getAsInt();
		}
		else {
			throw new IllegalArgumentException("Didn't find an int in the range not in the input array.");
		}
		return missingValue;
	}
	
	/** Java 7 type implementation for comparison purposes.
	 * This is a fairly naive implementation, a better one is also implemented.
	 * 
	 * @param arr The array, which does not need to be sorted.
	 * @param first The first value to check.
	 * @param last The last value to check.
	 * @return The first value in the range which is not present in the array.
	 * @throws IllegalArgumentException If the array parameter is null, or there is no value not present in the array.
	 */
	public int findFirstNotInArrayTediousJava7(Integer[] arr, int first, int last) 
			throws IllegalArgumentException {
		if (arr == null) {
			String msg = "Array parameter is null in call to findFirstNotInArrayTediousJava7()";
			LOGGER.warn(msg);
			throw new IllegalArgumentException(msg);
		}

		// Make sure the parameters are in order.
		int start = Math.min(first, last);
		int end = Math.max(first, last);
		
		int missingValue = NOT_FOUND;
		
		// Sort the array, so numbers are searched for in order.
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
	
	public int findFirstNotInArrayUsingSet(Integer[] arr, int first, int last)
			throws IllegalArgumentException {
		if (arr == null) {
			String msg = "Array parameter is null in call to findFirstNotInArrayUsingSet()";
			LOGGER.warn(msg);
			throw new IllegalArgumentException(msg);
		}
		
		int missingValue = NOT_FOUND;
		Set<Integer> intSet = new HashSet<>(Arrays.asList(arr));
		for (int i=first; i<=last; i++) {
			if (!intSet.contains(i) ) {
				missingValue = i;
				break;
			}
		}
		
		return missingValue;
	}
	
	/** Search the sorted array for the specified value.
	 * 
	 * @param arr The input parameter
	 * @param value The value being searched for.
	 * @param index The first index of the value in the array or NOT_FOUND if not found.
	 * @return The array index of the value, or NOT_FOUND if not found.
	 */
	protected int indexOfValue(Integer[] arr, int value, int index) {
		int valueIndex = NOT_FOUND;
		for (int i=index; i<arr.length; i++) {
			if (arr[i] == value) {
				valueIndex = i;
				break;
			}
			else if (arr[i] > value) {
				valueIndex = NOT_FOUND;
				break;
			}
		}
		
		return valueIndex;
	}
	
	/** Determine the efficiency of different implementations for the given input size.
	 *  Generate random values based on the input parameter, run the different implementations,
	 *  and set the time for each.
	 *  
	 * @param size The number of random values to use.
	 * @return The dto with the timing information.
	 */
	public FindFirstEfficiencyDto testEfficiency(int size) {
		FindFirstEfficiencyDto dto = new FindFirstEfficiencyDto(size);
		int expected = size / 2;
		Integer[] arr = arrayUtils.getSortedIntegerArrayWithMissingValue(size, expected);
		arr = arrayUtils.scrambleTestArray(arr);
		
		Instant start = Instant.now();
		int found = findFirstNotInArrayUsingStreams(arr, 0, size + 1);
		if (found != expected) {
			dto.setSuccessful(false);
			String errMsg = "findFirstNotInArrayUsingStreams() failed, expected " + expected + ", got " + found;
			dto.setErrorMessage(errMsg);
		}
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		dto.setStreamTimeMsec(duration.getTotalMsec());
		
		start = Instant.now();
		found = findFirstNotInArrayTediousJava7(arr, 0, size + 1);
		if (found != expected) {
			dto.setSuccessful(false);
			String errMsg = "findFirstNotInArrayTediousJava7() failed, expected " + expected + ", got " + found;
			dto.setErrorMessage(errMsg);
		}
		end = Instant.now();
		duration = new MsecDuration(start, end);
		dto.setTediousTimeMsec(duration.getTotalMsec());
		
		start = Instant.now();
		found = findFirstNotInArrayUsingSet(arr, 0, size + 1);
		if (found != expected) {
			dto.setSuccessful(false);
			String errMsg = "findFirstNotInArrayUsingSet() failed, expected " + expected + ", got " + found;
			dto.setErrorMessage(errMsg);
		}
		end = Instant.now();
		duration = new MsecDuration(start, end);
		dto.setSetTimeMsec(duration.getTotalMsec());
		
		return dto;
	}
}
