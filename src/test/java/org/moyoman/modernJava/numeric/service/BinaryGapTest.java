package org.moyoman.modernJava.numeric.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BinaryGapTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(BinaryGapTest.class);
	
	@Autowired
	private BinaryGap binaryGap;
	
	@Test
	/** Test assorted values for correctness.
	 *  Note the fancy case statement, which will only compile in Java 12 or greater.
	 */
	public void testVariousValues() {		
		for (Integer i=0; i<=24; i++) {
			Integer singleSolution = binaryGap.singleSolution(i);
			Integer betterSolution = binaryGap.betterSolution(i);
			LOGGER.info("Input {}, binary is {}, gap {}, gap2 {}", 
					i, Integer.toBinaryString(i), singleSolution, betterSolution);
			Assert.assertEquals(singleSolution, betterSolution);
			
			int intValue = betterSolution.intValue();
			switch(i) {
			case 0, 1, 2, 3, 4, 6, 7, 8, 12, 14, 15, 16, 24 -> Assert.assertEquals(0, intValue);
			case 5, 10, 11, 13, 20, 21, 22, 23 -> Assert.assertEquals(1, intValue);
			case 9, 18, 19 -> Assert.assertEquals(2, intValue);
			case 17 -> Assert.assertEquals(3, intValue);
			default -> Assert.fail("Value " + intValue + " not tested correctly");
			}			
		}
	}
	
	@Test
	public void testLargeRange() { 
		for (Integer i=0; i<=1100000; i++) {
			Integer singleSolution = binaryGap.singleSolution(i);
			Integer betterSolution = binaryGap.betterSolution(i);
			Assert.assertEquals(singleSolution, betterSolution);
		}
	}
	
	@Test
	/** Test all problematic values.
	 *  These are the specific inputs which caused the inelegant singleSolution() method
	 *  to fail.
	 */
	public void testSpecificValues() {
		Assert.assertEquals(1, binaryGap.singleSolution(13));
		Assert.assertEquals(2, binaryGap.singleSolution(41));
		Assert.assertEquals(1, binaryGap.singleSolution(85));
	}
	
	@Test
	public void testGetOnes() {
		List<Integer> oneList = binaryGap.findOneBits(0);
		Assert.assertEquals(0, oneList.size());
		oneList = binaryGap.findOneBits(1);
		Assert.assertEquals(1, oneList.size());
		Assert.assertTrue(oneList.get(0) == 0);
		oneList = binaryGap.findOneBits(10);
		Assert.assertEquals(2, oneList.size());
		Assert.assertTrue(oneList.get(0) == 0);
		Assert.assertTrue(oneList.get(1) == 2);
		oneList = binaryGap.findOneBits(41);
		Assert.assertEquals(3, oneList.size());
		Assert.assertTrue(oneList.get(0) == 0);
		Assert.assertTrue(oneList.get(1) == 2);
		Assert.assertTrue(oneList.get(2) == 5);
	}
	
	@Test
	public void testFindMaxGap() {
		List<Integer> oneList = null;
		int gap = binaryGap.findMaxGap(oneList);
		Assert.assertEquals(0, gap);
		
		oneList = new ArrayList<>();
		gap = binaryGap.findMaxGap(oneList);
		Assert.assertEquals(0, gap);
		
		oneList.add(1);
		gap = binaryGap.findMaxGap(oneList);
		Assert.assertEquals(0, gap);
		
		oneList.add(2);
		gap = binaryGap.findMaxGap(oneList);
		Assert.assertEquals(0, gap);
		
		oneList.add(7);
		gap = binaryGap.findMaxGap(oneList);
		Assert.assertEquals(4, gap);
		
		oneList.add(10);
		gap = binaryGap.findMaxGap(oneList);
		Assert.assertEquals(4, gap);
		
		oneList.add(14);
		gap = binaryGap.findMaxGap(oneList);
		Assert.assertEquals(4, gap);
		
		oneList.add(21);
		gap = binaryGap.findMaxGap(oneList);
		Assert.assertEquals(6, gap);
		
		oneList.add(25);
		gap = binaryGap.findMaxGap(oneList);
		Assert.assertEquals(6, gap);
		
		oneList.add(31);
		gap = binaryGap.findMaxGap(oneList);
		Assert.assertEquals(6, gap);
	}
}
