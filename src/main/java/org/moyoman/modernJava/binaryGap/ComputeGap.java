package org.moyoman.modernJava.binaryGap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ComputeGap {
	/** Find the binary gap for the given input.
	 *  This appears to work, but is inelegant and error-prone.
	 *  
	 *  This is also hard to test.
	 *  
	 *  In running larger and larger values, I had to search for 3 different bugs to be fixed.
	 *  
	 * @param n The value to be tested.
	 * @return The number of consecutive 0 bits surrounded by a 1.
	 */
	public int singleSolution(int n) {
		String binaryStr = Integer.toBinaryString(n);
		int gapSize = 0;
		int currCount = 0;
		boolean startFlag = false;
		char[] carr = binaryStr.toCharArray();
		for (int i=0; i<carr.length; i++) {
			if (startFlag) {
				if (carr[i] == '0') {
					currCount++;
				}
				else if (currCount > 0)
				{
					if (currCount > gapSize) {
						gapSize = currCount;
					}
					currCount = 0;
				}
			}
			else if (carr[i] == '1') {
				startFlag = true;
			}
		}

		return gapSize;
	}
	
	/** Cleaner, more elegant code than singleSolution() method.
	 * 
	 *  @param n The value to be tested.
	 *  @return The number of consecutive 0 bits surrounded by a 1.
	 */
	public int betterSolution(int n) {
		int gapSize = 0;
		List<Integer> oneList = findOneBits(n);
		if (oneList.size() < 2) {
			gapSize = 0;
		}
		else {
			for (int i=0; i<oneList.size() - 1; i++) {
				int currGap = (oneList.get(i+1) - oneList.get(i)) -1;
				if (currGap > gapSize) {
					gapSize = currGap;
				}
			}			
		}
		
		return gapSize;
	}
	
	/** Find a list of the indices of the 1 bits in order.
	 * @param n The input parameter
	 * @return The indices of the 1 bits.
	 */
	protected List<Integer> findOneBits(int n) {
		List<Integer> oneList = new ArrayList<>();
		String binaryString = Integer.toBinaryString(n);
		char[] carr = binaryString.toCharArray();
		for (int i=0; i<carr.length; i++) {
			if (carr[i] == '1') {
				oneList.add(i);
			}
		}
		
		return oneList;
	}
}
