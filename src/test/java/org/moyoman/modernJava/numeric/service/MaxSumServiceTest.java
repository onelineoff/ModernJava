package org.moyoman.modernJava.numeric.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MaxSumServiceTest {
	@Autowired
	private MaxSumService maxSumService;
	
	@Test
	public void testGetCategoryMap() {
		Integer[] arr = {1,4,7,11,114,41,44, 4444441, 77,1111,45654,74447};
		
		Map<String, List<Integer>> categoryMap = maxSumService.getCategoryMap(arr);
		Assert.assertEquals(5, categoryMap.size());
		Map<String, List<Integer>> expectedMap = new HashMap<>();
		List<Integer> expected11List = new ArrayList<>();
		expected11List.add(1);
		expected11List.add(11);
		expected11List.add(1111);
		expectedMap.put("11", expected11List);
		
		List<Integer> expected44List = new ArrayList<>();
		expected44List.add(4);
		expected44List.add(44);
		expected44List.add(45654);
		expectedMap.put("44", expected44List);
		List<Integer> expected77List = new ArrayList<>();
		expected77List.add(7);
		expected77List.add(77);
		expected77List.add(74447);
		expectedMap.put("77", expected77List);
		
		List<Integer> expected14List = new ArrayList<>();
		expected14List.add(114);
		expectedMap.put("14", expected14List);
		
		List<Integer> expected41List = new ArrayList<>();
		expected41List.add(41);
		expected41List.add(4444441);
		expectedMap.put("41", expected41List);
		
		Assert.assertEquals(expectedMap, categoryMap);
	}
	
	@Test
	public void testGetIntToStringKey() {
		Assert.assertEquals("99", maxSumService.getIntToStringKey(9));
		Assert.assertEquals("99", maxSumService.getIntToStringKey(9009));
		Assert.assertEquals("99", maxSumService.getIntToStringKey(999));
		Assert.assertEquals("19", maxSumService.getIntToStringKey(123456789));
		Assert.assertEquals("91", maxSumService.getIntToStringKey(987654321));
		Assert.assertEquals("33", maxSumService.getIntToStringKey(345676543));
	}
	
	@Test
	public void testGetMaxValue() {
		Integer[] arr = {1, 2, 3, 444444444, 2222222, 111, 12321};
		Assert.assertEquals(444444444, maxSumService.getMaxValue(arr, 1));
		Assert.assertEquals(2222224, maxSumService.getMaxValue(arr, 2));
		Assert.assertEquals(12433, maxSumService.getMaxValue(arr, 3));		
	}
	
}
