package org.moyoman.modernJava.util;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Utility methods for performing various computations.
 *  The methods are made static rather than making this a service
 *  to allow for quicker unit testing without requiring spring boot.
 *  
 */
public class MathUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(MathUtils.class);
	
	/** Return the exclusive or (xor) of the input values.
	 *  This can be used for comparing sets of long values for equality without
	 *  having arbitrarily large memory requirements.
	 *  
	 *  Similar to the hashCode() method on Object, it implies, but does not
	 *  guarantee equality if two xor values are equal.
	 * 
	 * @param inputs A collection of long values
	 * @return The xor value.
	 */
	public static long xor(Collection<Long> inputs) throws IllegalArgumentException {
		Optional<Long> result = inputs.stream().reduce((i, j) -> i ^ j);
		if (result.isEmpty()) {
			String msg = "MathUtils.xor called with empty argument";
			LOGGER.warn(msg);
			throw new IllegalArgumentException(msg);
		}
		else {
			return result.get().longValue();
		}
			
	}
}
