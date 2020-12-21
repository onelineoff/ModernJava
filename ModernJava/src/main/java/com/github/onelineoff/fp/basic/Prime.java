package com.github.onelineoff.fp.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.LongPredicate;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import com.github.onelineoff.fp.util.LoadUtils;

/** Implement methods for determine if numbers are prime, using old-style and FP constructs.
 */
public class Prime {
	
	/** Find the primes between 0 and the input parameter.
	 * 
	 * @param max The largest number to check.
	 * @return A list of all the primes in the range being tested.
	 */
	public List<Long> getPrimesOldStyle(long max) {
		LoadUtils utils = new LoadUtils();
		List<Long> outputList = new ArrayList<>();
		for (long i=0; i<=max; i++) {
			if (utils.isPrime(i)) {
				outputList.add(i);
			}
		}
		
		return outputList;
	}
	
	/** Find all the primes in the specified range.
	 *  Use streaming functionality.
	 * 
	 * @param max The maximum number to be checked.
	 * @param isParallel - Use parallel streams if true, otherwise false.
	 * 
	 * @return A list of all the primes in the range being tested.
	 */
	public List<Long> getPrimesAsLongStream(long max, boolean isParallel) {
		LoadUtils utils = new LoadUtils();
		LongPredicate isPrime = utils::isPrime; // Subtle difference between LongPredicate and Predicate<Long>
		LongStream ls = LongStream.rangeClosed(0,  max);
		if (isParallel) {
			ls = ls.parallel();
		}
		else {
			ls = ls.sequential();
		}
		List<Long> retList = ls.filter(isPrime).boxed().collect(Collectors.toList());
		return retList;
	}
}
