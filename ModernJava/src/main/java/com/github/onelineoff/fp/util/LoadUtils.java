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
	public boolean isPrime(Integer val) {
		boolean retVal = true;

		val = Math.abs(val);
		if (val < 4) {
			retVal = true;
		}
		else {
			int max = val / 2;
			for (int i=2; i<val; i++) {
				for (int j=i; j<=max; j++) {
					if (i * j == val) {
						retVal = false;
					}
				}
			}			
		}
		
		return retVal;
	}
}
