package org.moyoman.modernJava.numeric.service;

import org.springframework.stereotype.Service;

/** Another coding interview type problem.
 * 
 *  An array contains integer values, which are categorized by their first and last digits,
 *  so 1099 and 19 fall into the same category.
 *  
 *  1111 and 1 would also fall into the same category.
 *  
 *  The problem is to find the largest sum which is produced by two values from the same category.
 *  If there are not multiple values in any category, then the sentinel value is returned.
 *  
 *  The problem will also be generalized to find the largest sum of the top N values from the same category.
 *  Various solutions are presented here, from the simple, error prone one I wrote during a live coding exercise,
 *  to a professional quality (hopefully) implementation that might be used in production code.
 */
@Service
public class MaxSumService {
	public static final Integer SENTINEL = -1;
	public int getMaxValue(int[] arr) {
		int maxValue = SENTINEL;
		if (arr == null || arr.length < 2) {
			maxValue = SENTINEL;
		}
		else {
			// TODO: Implement logic here.
		}
		
		return maxValue;
	}
}
