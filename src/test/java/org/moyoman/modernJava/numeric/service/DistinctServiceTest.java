package org.moyoman.modernJava.numeric.service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.moyoman.modernJava.dto.MsecDuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistinctServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(DistinctServiceTest.class);
	
	private static final Integer LARGE_ARRAY_SIZE = 10000000;
	private static final Integer MAX_ARRAY_VALUE = LARGE_ARRAY_SIZE + (int) (LARGE_ARRAY_SIZE * 0.1f);
	
	@Autowired
	private DistinctService distinct;
	
	@Test
	public void testDistinct() {
		Integer[] arr = {1,5,8,2,5,2,1,4};
		
		Assert.assertEquals(5, distinct.findDistinctUsingStreams(arr));
		Assert.assertEquals(5, distinct.findDistinctNonFP(arr));
		
		Integer[] arr2 = {1,5,8,2,5,2,1,4,3,7,7,7,4};
		Assert.assertEquals(7, distinct.findDistinctUsingStreams(arr2));
		Assert.assertEquals(7, distinct.findDistinctNonFP(arr2));
	}
	
	@Test
	// TODO: Get xor value from each to verify the results.
	public void testEfficiency() {
		// Generate a large amount of random test data to use for the two different methods.
		Random random = new Random(Instant.now().getEpochSecond());
		Integer[] dataArray = new Integer[LARGE_ARRAY_SIZE];
		Set<Integer> elementSet = new HashSet<>();
		
		for (int i=0; i<LARGE_ARRAY_SIZE; i++) {
			dataArray[i] = random.nextInt(MAX_ARRAY_VALUE + 1);
			elementSet.add(dataArray[i]);
		}
		
		// The number of distinct values that the service methods should return.
		long distinctValues = elementSet.size();
		
		Instant start = Instant.now();
		long distinctCount = distinct.findDistinctUsingStreams(dataArray);
		Instant end = Instant.now();
		Assert.assertEquals(distinctValues, distinctCount);
		MsecDuration duration = new MsecDuration(start, end);
		LOGGER.info("findDistinctUsingStreams found {} distinct of {}, in {}", distinctCount, LARGE_ARRAY_SIZE, duration);
		
		start = Instant.now();
		long nonFpDistinctCount = distinct.findDistinctNonFP(dataArray);
		end = Instant.now();
		Assert.assertEquals(distinctValues, nonFpDistinctCount);
		duration = new MsecDuration(start, end);
		LOGGER.info("findDistinctNonFP found {} distinct of {}, in {}", nonFpDistinctCount, LARGE_ARRAY_SIZE, duration);
	}
}
