package org.moyoman.modernJava.numeric.service;

/** Another coding interview type problem.
 *  An array of integers should contain either 0 of an integer
 *  or the same number of entries as the value, e.g., there should
 *  be 0 or 3 elements with the value 3, 0 or 8 elements with the
 *  value 8, etc.
 *  
 *  A move consists of removing an existing element, or inserting a new one.
 *  The problem is to determine the minimum number of moves necessary until
 *  the array meets the criterion explained above.
 *  
 *  Performing the actual list manipulation is not part of the problem.
 *  A null or empty array should return 0.
 *
 */
public class MinimumMoves {
	public int getMinimumMoves(int[] arr) {
		int moveCount = 0;
		if (arr == null || arr.length == 0) {
			moveCount = 0;
		}
		else {
			// TODO Implement logic here.
		}
		
		return moveCount;
	}
}
