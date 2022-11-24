package org.moyoman.modernJava.prime.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.LongStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/** Implementations of finding prime numbers using different concurrency options.*/
public class PrimeService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PrimeService.class);
	private static final long MIN_VALUE = 3;
	private static final int MAX_THREADS = 12;
	
	@Autowired
	private PrimeCalculator primeCalculator;
	
	public boolean isPrime(long candidate) {
		return primeCalculator.isPrime(candidate);
	}
	
	public Long getNextPrime(long previousValue) {
		long nextPrime = primeCalculator.getNextPrime(previousValue);
		LOGGER.debug("For {}, next prime is {}", previousValue, nextPrime);
		return nextPrime;
	}
	
	/** Get the prime numbers in the specified range.
	 *  This is the naive implementation, make sequential
	 *  calls for each value and return as a list.
	 *  
	 * @param startValue The first value to check.
	 * @param endValue The last value to check.
	 * @return A list of the prime values in the range.
	 */
	public List<Long> getPrimes(long startValue, long endValue) {
		// Verify parameter values
		long first = Math.min(startValue, endValue);
		long second = Math.max(startValue, endValue);
		first = Math.max(first, MIN_VALUE);
		second = Math.max(second, MIN_VALUE);
		
		List<Long> primeList = new ArrayList<>();
		for (long i = first; i<=second; i++) {
			if (primeCalculator.isPrime(i)) {
				primeList.add(i);
			}
		}
		return primeList;		
	}	
	
	/** Get the prime numbers in the range using Java 8 FP.
	 * 
	 * @param startValue The first value to check.
	 * @param endValue The last value to check.
	 * @return A list of the prime values in the range.
	 */
	public List<Long> getPrimesThroughStream(long startValue, long endValue) {
		// Verify parameter values
		long first = Math.min(startValue, endValue);
		long second = Math.max(startValue, endValue);
		first = Math.max(first, MIN_VALUE);
		second = Math.max(second, MIN_VALUE);
		return LongStream.rangeClosed(first, second).parallel().filter(i -> i % 2 == 1).
				filter(i -> primeCalculator.isPrime(i)).
				collect(ArrayList::new, ArrayList::add, ArrayList::addAll);				
	}

	public List<Long> getPrimesThroughAlternateStreams(long startValue, long endValue) {
		// Verify parameter values
				long first = Math.min(startValue, endValue);
				long second = Math.max(startValue, endValue);
				first = Math.max(first, MIN_VALUE);
				second = Math.max(second, MIN_VALUE);
				return LongStream.rangeClosed(first, second).filter(i -> i % 2 == 1).parallel().
						filter(i -> primeCalculator.isPrime(i)).
						collect(ArrayList::new, ArrayList::add, ArrayList::addAll);			
	}
	
	/** Use Java 7 concurrency to parallelize the prime checking calls.
	 * 
	 @ param startValue The first value to check.
	 * @param endValue The last value to check.
	 * @return A list of the prime values in the range.
	 */
	public List<Long> getPrimesThroughJava7Threading(long startValue, long endValue) {
		List<Long> primeList = new ArrayList<>();
		
		try {
			// Verify parameter values
			long first = Math.min(startValue, endValue);
			long second = Math.max(startValue, endValue);
			first = Math.max(first, MIN_VALUE);
			second = Math.max(second, MIN_VALUE);

			List<PrimeCallable> runnableList = new ArrayList<>();
			for (long l = startValue; l<= endValue; l++) {
				PrimeCallable primeRunnable = new PrimeCallable(primeCalculator, l);
				runnableList.add(primeRunnable);
			}
			ExecutorService es = Executors.newFixedThreadPool(MAX_THREADS);
			List<Future<Long>> resultList = es.invokeAll(runnableList);
			for (Future<Long> result : resultList) {
				Long prime = result.get();
				if (prime != null) {
					primeList.add(prime);
				}
			}
			
		} catch (Exception e) {
			LOGGER.warn("Error getting primes", e);
		}
		return primeList;
	}
}
