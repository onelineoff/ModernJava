/** This class represents concepts from the O'Reilly book,
 *  A Functional Approach to Java, by Ben Weidig.
 */
package org.moyoman.modernJava.fp;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

public class FAJ {

	// Different levels of verbosity for the isNotNull functionality.
	public Predicate<String> isNotNull = input -> input != null;
	public Predicate<String> isNotNullV1 = (String input) -> {return input != null;};
	public Predicate<String> isNotNullV2 = input -> {return input != null;};
	public Predicate<String> isNotNullV3 = (String input) -> input != null;
	
	// Convert the Predicate to a Function.
	public Function<String, Boolean> funcIsNotNull = input -> isNotNull.test(input);
	
	/** Example of a lambda implementing an interface.*/
	public NotNullInterface nni = input -> input != null;
	
	public int findRange(Collection<Integer> collection) {
		return 0;
	}
	
	public int findMostFrequent(Collection<Integer> collection) {
		return 0;
	}
	
	public int findMedian(Collection<Integer> collection) {
		return 0;		
	}
}

