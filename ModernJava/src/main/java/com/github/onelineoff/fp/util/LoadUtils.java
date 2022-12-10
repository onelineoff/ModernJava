package com.github.onelineoff.fp.util;

/** Implement computationally intensive methods for performing load testing.
 */
public class LoadUtils implements Utils {

	/** Naive implementation of method to determine if value is prime.
	 * 
	 * @param val The value to be tested.
	 * @return True if the parameter is a prime number, otherwise false.
	 */
	@Override
	public boolean isPrime(Long val) {
		boolean retVal = true;
		
		val = Math.abs(val);
		if (val < 4) {
			retVal = true;
		}
		else {
			long max = val / 2;
			for (long i=2; i<val; i++) {
				for (long j=i; j<=max; j++) {
					if (i * j == val) {
						retVal = false;
						break;
					}
				}
			}			
		}	
		return retVal;
	}
}
