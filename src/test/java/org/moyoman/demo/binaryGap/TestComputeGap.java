package org.moyoman.demo.binaryGap;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
public class TestComputeGap {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestComputeGap.class);
	
	@Autowired
	private ComputeGap computeGap;
	
	@Test
	public void testVariousValues() {
		Set<Integer> oneSet = new HashSet<>();
		oneSet.add(5);
		oneSet.add(10);
		oneSet.add(11);
		oneSet.add(13);
		oneSet.add(20);
		oneSet.add(21);
		oneSet.add(22);
		oneSet.add(23);
		
		Set<Integer> twoSet = new HashSet<>();
		twoSet.add(9);
		twoSet.add(18);
		twoSet.add(19);
		
		Set<Integer> threeSet = new HashSet<>();
		threeSet.add(17);
		
		for (Integer i=0; i<=24; i++) {
			Integer singleSolution = computeGap.singleSolution(i);
			Integer betterSolution = computeGap.betterSolution(i);
			LOGGER.info("Input {}, binary is {}, gap {}, gap2 {}", 
					i, Integer.toBinaryString(i), singleSolution, betterSolution);
			Assert.assertEquals(singleSolution, betterSolution);
			
			switch(singleSolution) {
			case 1:
				Assert.assertTrue(oneSet.contains(i));
				break;
			case 2:
				Assert.assertTrue(twoSet.contains(i));
				break;
			case 3:
				Assert.assertTrue(threeSet.contains(i));
				break;
				default:
					Assert.assertFalse(oneSet.contains(i));
					Assert.assertFalse(twoSet.contains(i));
					Assert.assertFalse(threeSet.contains(i));
					break;
			}			
		}
	}
	
	@Test
	public void testLargeRange() { 
		for (Integer i=0; i<=1100000; i++) {
			Integer singleSolution = computeGap.singleSolution(i);
			Integer betterSolution = computeGap.betterSolution(i);
			Assert.assertEquals(singleSolution, betterSolution);
		}
	}
	
	@Test
	/** Test all problematic values.
	 */
	public void testSpecificValues() {
		Assert.assertEquals(1, computeGap.singleSolution(13));
		Assert.assertEquals(2, computeGap.singleSolution(41));
		Assert.assertEquals(1, computeGap.singleSolution(85));
	}
	
	@Test
	public void testGetOnes() {
		List<Integer> oneList = computeGap.findOneBits(0);
		Assert.assertEquals(0, oneList.size());
		oneList = computeGap.findOneBits(1);
		Assert.assertEquals(1, oneList.size());
		Assert.assertTrue(oneList.get(0) == 0);
		oneList = computeGap.findOneBits(10);
		Assert.assertEquals(2, oneList.size());
		Assert.assertTrue(oneList.get(0) == 0);
		Assert.assertTrue(oneList.get(1) == 2);
		oneList = computeGap.findOneBits(41);
		Assert.assertEquals(3, oneList.size());
		Assert.assertTrue(oneList.get(0) == 0);
		Assert.assertTrue(oneList.get(1) == 2);
		Assert.assertTrue(oneList.get(2) == 5);
	}
}
