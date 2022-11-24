package org.moyoman.modernJava.firstInt;

import java.util.Arrays;
import java.util.HashSet;
import java.util.OptionalInt;
import java.util.Set;
import java.util.stream.IntStream;

public class FindNewInteger {
	public int findFirstNotInArray(Integer[] arr, int first, int last) throws IllegalArgumentException {
		verifyInputs(arr, first, last);

		int retValue;
		Set<Integer> intSet = new HashSet<>(Arrays.asList(arr));
		OptionalInt optionalInt =IntStream.rangeClosed(first, last).filter(i -> !intSet.contains(i)).findFirst();
		
		if (optionalInt.isPresent()) {
			retValue = optionalInt.getAsInt();
		}
		else {
			throw new IllegalArgumentException("Didn't find an int in the range not in the input array.");
		}
		return retValue;
	}

	
	protected void verifyInputs(Integer[] arr, int first, int last) throws IllegalArgumentException {
		if (arr == null || first > last) {
			throw new IllegalArgumentException("Invalid input parameters for findFirstNotInArray()");
		}
		
	}
}
