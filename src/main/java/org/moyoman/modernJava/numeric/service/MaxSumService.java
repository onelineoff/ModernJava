package org.moyoman.modernJava.numeric.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(MaxSumService.class);
	
	public static final Integer SENTINEL = -1;
	
	/** Get the maximum sum of the largest ntuple of numbers in the same category.
	 * 
	 * @param arr The array of values
	 * @param topNum The number of values to add.
	 * @return The maximum sum of values in the same category.
	 */
	public int getMaxValue(Integer[] arr, int topNum) {
		int maxValue = SENTINEL;
		if (arr == null || arr.length < topNum) {
			maxValue = SENTINEL;
		}
		else {
			Map<String, List<Integer>> categoryMap = getCategoryMap(arr);
			
			// For each list, get the largest topNum values, add them, and return the largest one.
			for (Entry<String, List<Integer>> currEntry : categoryMap.entrySet()) {
				List<Integer> currList = currEntry.getValue();
								
				if (currList.size() < topNum) {
					LOGGER.debug("Current list {} contains {} entries, less than {}", 
							currEntry.getKey(), currList.size(), topNum);
				}
				else {
					currList.sort((i1, i2) -> i1 - i2);
					int sum = 0;
					for (int i=0; i<topNum; i++) {
						sum += currList.get(i);
					}
					if (sum > maxValue) {
						LOGGER.debug("Found new max value {} for {}, topNum is {}", maxValue, sum, topNum);
						maxValue = sum;						
					}
				}
			}
		}
		
		return maxValue;
	}
	
	/** Produce a map of lists with elements in the appropriate list.
	 *  The categories are as explained at the top of this file.
	 *  Create a map where the key is the category, and the value is a list of integers.
	 *  
	 * @param arr The input array.
	 * @return A map with the elements of the array mapped to the appropriate category.
	 */
	protected Map<String, List<Integer>> getCategoryMap(Integer[] arr) {
		Map<String, List<Integer>> categoryMap = new HashMap<>();
		for (Integer i : arr) {
			String key = getIntToStringKey(i);
			List<Integer> currList = categoryMap.get(key);
			if (currList == null) {
				currList = new ArrayList<>();
				categoryMap.put(key, currList);
			}
			currList.add(i);
		}
		
		return categoryMap;
	}
	
	/** Return a string which is the first and last characters for an integer.
	 *  For example, 1099 would return the string 19.
	 *  For a value less than 10, the digit would repeat, e.g., 5 returns 55.
	 *  
	 * @param element The input parameter
	 * @return The string representation.
	 */
	protected String getIntToStringKey(int element) {
		String str = Integer.toString(element);
		char[] carr = new char[2];
		carr[0] = str.charAt(0);
		carr[1] = str.charAt(str.length() - 1);
		return new String(carr);		
	}
}
