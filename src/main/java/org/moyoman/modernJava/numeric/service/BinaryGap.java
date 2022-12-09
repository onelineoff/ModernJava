package org.moyoman.modernJava.numeric.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** Solution to the binary gap problem.
 *  @see <A href="https://app.codility.com/programmers/lessons/1-iterations/binary_gap/">Codility Link</A>
 * 
 * A binary gap within a positive integer N is any maximal sequence of consecutive zeros 
 * that is surrounded by ones at both ends in the binary representation of N.
 * 
 * A number of solutions are presented here, as the code is progressively refined.
 *
 */
@Service
public class BinaryGap {
	private static final Logger LOGGER = LoggerFactory.getLogger(BinaryGap.class);
	
	/** Find the binary gap for the given input.
	 *  This appears to work, but is inelegant and error-prone.
	 *  
	 *  This is also hard to test.
	 *  In running larger and larger values, I had to search for 3 different bugs to be fixed.
	 *  
	 *  What's bad about this?
	 *  <UL>
	 *  <LI>Nested if/else structure is hard to understand.
	 *  <LI>Big blob of code which is difficult to test.
	 *  <LI>Error prone and difficult to verify without testing the whole method.
	 *  </UL>
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

		LOGGER.info("singleSolution() found binary gap for {}, binary is {}, gap is {}", n, binaryStr, gapSize);
		return gapSize;
	}
	
	/** Cleaner, more elegant code than singleSolution() method.
	 * 
	 * Question: Can this be rewritten more elegantly using streams?
	 * My understanding is, no, because it involves comparing two adjacent elements
	 * in the stream, rather than a unary operator such as sum or max.
	 * 
	 * There is an oss project which provides this functionality, but I have not
	 * investigated it to see how well it would work, or if it actually makes the code simpler
	 * or better in any way.
	 * 
	 *  @param n The value to be tested.
	 *  @return The number of consecutive 0 bits surrounded by a 1.
	 */
	public int betterSolution(int n) {
		List<Integer> oneList = findOneBits(n);
		int gapSize = findMaxGap(oneList);
		
		LOGGER.info("betterSolution() found binary gap for {}, gap is {}", n, gapSize);
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
	
	/** Find the largest gap in the list.
	 *  A list with less than 2 entries has a gap of 0.
	 * @param oneList A sorted list.
	 * @return The largest difference between 2 adjacent entries.
	 */
	protected int findMaxGap(List<Integer> oneList) {
		int gapSize = 0;
		if (oneList == null || oneList.size() < 2) {
			gapSize = 0;
		}
		else {
			for (int i=0; i<oneList.size() - 1; i++) {
				int currGap = (oneList.get(i+1) - oneList.get(i)) - 1;
				if (currGap > gapSize) {
					gapSize = currGap;
				}
			}			
		}
		
		return gapSize;
	}
}
