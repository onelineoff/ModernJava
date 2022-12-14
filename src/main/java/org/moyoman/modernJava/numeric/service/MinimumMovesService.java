package org.moyoman.modernJava.numeric.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** Another coding interview type problem.
 * 
 *  An array of integers should contain either 0 of an integer
 *  or the same number of entries as the value, e.g., there should
 *  be 0 or 3 elements with the value 3, 0 or 8 elements with the
 *  value 8, etc.
 *  
 *  A move consists of removing an existing element, or inserting a new one.
 *  The problem is to determine the minimum number of moves necessary until
 *  the array meets the criterion explained above.
 *  
 *  Performing the actual array element manipulation is not part of the problem.
 *  A null or empty array should return 0.
 *
 */
@Service
public class MinimumMovesService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MinimumMovesService.class);
	
	
	/** Get the minimum number of moves.
	 * 
	 * @param arr The input parameter
	 * @return The minimum number of moves to make the array meet the criterion.
	 */
	public int getMinimumMoves(Integer[] arr) {
		int moveCount = 0;
		if (arr != null) {
			Map<Integer, Integer> elementMap = getElementCount(arr);
			moveCount = getMinimumMoves(elementMap);
		}
		
		LOGGER.info("Number of moves for {} is {}", arr, moveCount);
		return moveCount;
	}
	
	/** Get the minimum number of moves
	 * 
	 * @param elementMap A map of the number of occurrences of each value.
	 * @return The minimum number of moves to meet the criterion.
	 */
	protected int getMinimumMoves(Map<Integer, Integer> elementMap) {
		int count = 0;
		Optional<Integer> retVal = elementMap.entrySet().stream().map(
				e -> Math.min(e.getValue(), Math.abs(e.getKey() - e.getValue()))).reduce(Integer::sum);
		if (retVal.isPresent()) {
			count = retVal.get();
		}
		
		LOGGER.info("Number of moves for {} is {}", elementMap, count);
		return count;
	}
	
	/** Create a map with the key as the element, and the value the count in the array.
	 * 
	 * @param arr The input parameter
	 * @return The map with the count of each element.
	 */
	protected Map<Integer, Integer> getElementCount(Integer[] arr) {
		if (arr == null) {
			return new HashMap<Integer, Integer>();
		}
		else {
			Map<Integer, Integer> elementMap = Arrays.stream(arr).collect(Collectors.toMap(i -> i, i -> 1, Integer::sum));
			LOGGER.info("Element count for {} is {}", arr, elementMap);
			
			return elementMap;
		}
	}
}
