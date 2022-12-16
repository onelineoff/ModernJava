package org.moyoman.modernJava.numeric.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MinimumMovesServiceTest {
	@Autowired
	private MinimumMovesService minimumMovesService;
	
	@Test
	public void testMinimumMoves() {
		Integer[] nullArray = null;
		Assert.assertEquals(0, minimumMovesService.getMinimumMoves(nullArray));
		Integer[] arr = new Integer[0];
		Assert.assertEquals(0, minimumMovesService.getMinimumMoves(arr));
		Integer[] arr2 = {1,1,3,4,4,4};
		Assert.assertEquals(3, minimumMovesService.getMinimumMoves(arr2));
		Integer[] arr3 = {1,2,2,2,5,5,5,8};
		Assert.assertEquals(4, minimumMovesService.getMinimumMoves(arr3));
		Integer[] arr4 = {10,10,10};
		Assert.assertEquals(3, minimumMovesService.getMinimumMoves(arr4));
		Integer[] arr5 = {13};
		Assert.assertEquals(1, minimumMovesService.getMinimumMoves(arr5));
	}
	
	@Test
	public void testGetElementMap() {
		Integer[] arr = {1,2,3,4,1,2,3,4,5,6,7,8,1,2,3,4};
		Map<Integer, Integer> map =  minimumMovesService.getElementCount(arr);
		Set<Integer> keySet = map.keySet();
		for (Integer key : keySet) {
			if (key<= 4) {
				Assert.assertEquals(3, map.get(key).intValue());
			}
			else {
				Assert.assertEquals(1, map.get(key).intValue());
			}
		}
	}
	
	@Test
	public void testMinimumMovesFromMap() { 
		int expectedValue = 0;
		Map<Integer, Integer> map = new HashMap<>();
		
		// These are already expected, nothing to be done.
		IntStream.range(1, 101).forEach(i -> map.put(i, i));
		
		// 5 moves required for each
		expectedValue += 500;
		IntStream.range(101, 201).forEach(i -> map.put(i, i - 5));
		
		// 5 moves required for each
		expectedValue += 500;
		IntStream.range(201, 301).forEach(i -> map.put(i, i + 5));
		
		// 2 moves required for each
		expectedValue += 200;
		IntStream.range(20001, 20101).forEach(i -> map.put(i, i + 2));
		
		// 2 moves required for each
		expectedValue += 200;
		IntStream.range(30001, 30101).forEach(i -> map.put(i, i - 2));
				
		int moveCount = minimumMovesService.getMinimumMoves(map);
		Assert.assertEquals(expectedValue, moveCount);
	}
}
