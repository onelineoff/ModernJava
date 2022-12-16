package org.moyoman.modernJava.util;

import java.time.Instant;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
/** Assorted utilities for processing Integer arrays.
 * 
 *  This type of functionality would generally be used by test classes,
 *  but due to the nature of this project, many of the REST calls use
 *  randomly generated Integer arrays.
 *  
 *  This is NOT meant to meet any secure cryptographic standards, so the
 *  Random class is used instead of SecureRandom.  This is encapsulated here,
 *  so it would be easy to change this in the future if desired.
 *  
 *  The Random and SecureRandom classes are NOT injectable, so can't be autowired.
 *
 */
public class ArrayUtils {
	private Random random;
	
	public ArrayUtils() {
		random = new Random(Instant.now().getEpochSecond());
	}
	
	/** Get an array of Integers with random values from 0 to maxValue.
	 * 
	 * @param size The size of the array.
	 * @param maxValue The maximum value that can be used.
	 * @return The new array.
	 */
	public Integer[] getRandomIntegerArray(int size, int maxValue) {
		Integer[] arr = new Integer[size];
		for (int i=0; i<size; i++) {
			arr[i] = random.nextInt(maxValue + 1);
		}
		
		return arr;
	}
	
	/** Get a sorted array with values from 0 to size.
	 * 
	 * @param size The size of the array
	 * @return The sorted array.
	 */
	public Integer[] getSortedIntegerArray(int size) {
		Integer[] arr = new Integer[size];
		for (int i=0; i<size; i++) {
			arr[i] = i;
		}
		
		return arr;
	}
	
	/** Get a sorted array with values from 0 to size + 1, skipping missingValue.
	 * 
	 * @param size The size of the array
	 * @param missingValue The value to skip
	 * @return The Integer array.
	 */
	public Integer[] getSortedIntegerArrayWithMissingValue(int size, int missingValue) {
		Integer[] arr = new Integer[size];
		for (int i=0; i<missingValue; i++) {
			arr[i] = i;
		}
		
		for (int i=missingValue; i<size; i++) {
			arr[i] = i+1;
		}
		
		return arr;
	}
	
	/** Randomly scramble the values in the array.
	 *  The scrambled array contains the same values,
	 *  just in a different order.
	 *  
	 * @param arr The input array.
	 * @return The same array with the scrambled values.
	 */
	public Integer[] scrambleTestArray(Integer[] arr) {
	int size = arr.length;
		// Randomly swap values in the array
		for (int i=0; i<size; i++) {
			int randomIndex = random.nextInt(size);
			int currValue = arr[i];
			int swapValue = arr[randomIndex];
			arr[i] = swapValue;
			arr[randomIndex] = currValue;
		}
		
		return arr;
	}
}
