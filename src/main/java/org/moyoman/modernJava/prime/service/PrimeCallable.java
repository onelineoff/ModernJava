package org.moyoman.modernJava.prime.service;

import java.util.concurrent.Callable;

/** Callable to allow for Java 7 style concurrency.
 */
public class PrimeCallable implements Callable<Long> {
	private PrimeCalculator primeCalculator;
	private long candidate;
	private boolean isPrime;
	
	public PrimeCallable(PrimeCalculator primeCalculator, long candidate) {
		this.primeCalculator = primeCalculator;
		this.candidate = candidate;
		isPrime = false;
	}

	public boolean isPrime() {
		return isPrime;
	}

	@Override
	public Long call() throws Exception {
		if (primeCalculator.isPrime(candidate)) {
			return candidate;
		}
		else {
			return null;
		}
	}
}
