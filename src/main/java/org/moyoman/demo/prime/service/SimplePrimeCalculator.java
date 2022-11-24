package org.moyoman.demo.prime.service;

import org.springframework.stereotype.Component;

/** Calculate prime numbers.
 *  By design, this is an extremely inefficient way to perform this calculation.
 *  The intention is to implement a computationally expensive operation so that
 *  tests can be performed on parallelization of this operation.
 *
 */
@Component
public class SimplePrimeCalculator implements PrimeCalculator {

	@Override
	public Long getNextPrime(long previousValue) {
		long candidate = previousValue + 1;
		if (candidate < 3) {
			candidate = 3;
		}
		
		boolean continueTesting = true;
		while (continueTesting) {
			boolean isPrime = isPrime(candidate);

			if (isPrime) {
				continueTesting = false;
			}
			else {
				candidate++;
			}
		}
		return candidate;
	}

	@Override
	public boolean isPrime(long candidate) {
		boolean isPrime = true;
		
		if (candidate < 3) {
			isPrime = false;
		}
		else if (candidate % 2 == 0) {
			isPrime = false;
		}
		else {
			for (long i = 2; i<candidate; i++) {
				for (long j=i; j<=candidate; j++) {
					if (i*j == candidate) {						
						isPrime = false;
					}
				}
			}
		}
		
		return isPrime;
		
	}
}
