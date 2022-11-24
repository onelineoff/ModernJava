package org.moyoman.demo.prime.service;

public interface PrimeCalculator {
	/** Determine if the input parameter is a prime number.
	 * 
	 * @param candidate The input parameter.
	 * @return true if candidate is a prime, or false.
	 */
	public boolean isPrime(long candidate);
	
	/** Get the next prime after the input parameter.
	 *  If the method is called with 3, the value 5 is returned.
	 *  
	 *  The minimum prime returned is 3, so any input parameter <=2
	 *  will return 3.
	 *  
	 * @param previousValue The value to start after.
	 * @return The first prime found.
	 */
	public Long getNextPrime(long previousValue);
}
