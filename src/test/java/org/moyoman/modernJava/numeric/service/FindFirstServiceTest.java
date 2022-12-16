package org.moyoman.modernJava.numeric.service;

import java.time.Instant;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moyoman.modernJava.dto.MsecDuration;
import org.moyoman.modernJava.util.TestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FindFirstServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(FindFirstServiceTest.class);

	private static final Integer LARGE_ARRAY_SIZE = 10000000;
	
	@Autowired
	FindFirstService findFirst;
	
	@Test
	public void testFindInt() {
		Integer[] arr1 = {1,2,3,4};
		Integer[] arr2 = {3, 4, 5, 7, 9};
		
		int val = findFirst.findFirstNotInArrayUsingStreams(arr1, 3, 6);
		Assert.assertEquals(5, val);
		val = findFirst.findFirstNotInArrayUsingStreams(arr2, 3, 12);
		Assert.assertEquals(6, val);
	}

	@Test
	public void testInvalidParameters() {
		try {
			Integer arr[] = null;
			findFirst.findFirstNotInArrayUsingStreams(arr, 3, 6);
			Assert.fail("Null array didn't throw exception");
		}
		catch(IllegalArgumentException iae) {
			// Expected result.
		}

		try {
			Integer[] arr1 = {1,2,3,4};
			findFirst.findFirstNotInArrayUsingStreams(arr1, 2, 3);
			Assert.fail("Incorrect parameters didn't throw exception");
		}
		catch(IllegalArgumentException iae) {
			// Expected result.
		}
	}
	
	@Test
	public void testFindFirstNotInArrayUsingStreams() {
		
		Integer[] arr = getLargeSortedTestArray();
		Instant start = Instant.now();
		int missing = findFirst.findFirstNotInArrayUsingStreams(arr, 0, LARGE_ARRAY_SIZE);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		int expected = LARGE_ARRAY_SIZE / 2;
		Assert.assertEquals(expected, missing);
		LOGGER.info("findFirstNotInArrayUsingStreams took {} for sorted array of size {}", duration, LARGE_ARRAY_SIZE);
		
		scrambleTestArray(arr);
		start = Instant.now();
		missing = findFirst.findFirstNotInArrayUsingStreams(arr, 0, LARGE_ARRAY_SIZE);
		end = Instant.now();
		duration = new MsecDuration(start, end);
		Assert.assertEquals(expected, missing);
		LOGGER.info("findFirstNotInArrayUsingStreams took {} for scrambled array of size {}", duration, LARGE_ARRAY_SIZE);
		
	}
	
	@Test
	public void testFindFirstNotInArrayTediousJava7() {
		Integer[] arr = getLargeSortedTestArray();
		Instant start = Instant.now();
		int missing = findFirst.findFirstNotInArrayTediousJava7(arr, 0, LARGE_ARRAY_SIZE);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		int expected = LARGE_ARRAY_SIZE / 2;
		Assert.assertEquals(expected, missing);
		LOGGER.info("findFirstNotInArrayTediousJava7 took {} for sorted array of size {}", duration, LARGE_ARRAY_SIZE);
		
		scrambleTestArray(arr);
		start = Instant.now();
		missing = findFirst.findFirstNotInArrayTediousJava7(arr, 0, LARGE_ARRAY_SIZE);
		end = Instant.now();
		duration = new MsecDuration(start, end);
		Assert.assertEquals(expected, missing);
		LOGGER.info("findFirstNotInArrayTediousJava7 took {} for scrambled array of size {}", duration, LARGE_ARRAY_SIZE);
	}
	
	@Test
	public void testFindFirstNotInArrayUsingSet() {
		Integer[] arr = getLargeSortedTestArray();
		Instant start = Instant.now();
		int missing = findFirst.findFirstNotInArrayUsingSet(arr, 0, LARGE_ARRAY_SIZE);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		int expected = LARGE_ARRAY_SIZE / 2;
		Assert.assertEquals(expected, missing);
		LOGGER.info("findFirstNotInArrayUsingSet took {} for sorted array of size {}", duration, LARGE_ARRAY_SIZE);
		
		scrambleTestArray(arr);
		start = Instant.now();
		missing = findFirst.findFirstNotInArrayUsingSet(arr, 0, LARGE_ARRAY_SIZE);
		end = Instant.now();
		duration = new MsecDuration(start, end);
		Assert.assertEquals(expected, missing);
		LOGGER.info("findFirstNotInArrayUsingSet took {} for scrambled array of size {}", duration, LARGE_ARRAY_SIZE);
	}
	private Integer[] getLargeSortedTestArray() {
		Integer[] arr = new Integer[LARGE_ARRAY_SIZE];
		
		// Populate array with ordered values except for the missing value.
		int missingValue = LARGE_ARRAY_SIZE / 2;
		for (int i=0; i<LARGE_ARRAY_SIZE; i++) {
			if (i < missingValue) {
				arr[i] = i;
			}
			else {
				arr[i] = i + 1;
			}
		}
		
		return arr;
	}
	
	private void scrambleTestArray(Integer[] arr) {
		// Randomly swap values in the array
		for (int i=0; i<LARGE_ARRAY_SIZE; i++) {
			int randomIndex = TestUtils.getRandomInt(LARGE_ARRAY_SIZE - 1);
			int currValue = arr[i];
			int swapValue = arr[randomIndex];
			arr[i] = swapValue;
			arr[randomIndex] = currValue;
		}
	}
}
