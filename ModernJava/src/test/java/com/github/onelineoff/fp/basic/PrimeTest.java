package com.github.onelineoff.fp.basic;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PrimeTest {
	@Test
	public void simplePrimeTest() {
		Prime prime = new Prime();
		List<Long> primeList = prime.getPrimesOldStyle(20);
		Assert.assertEquals(10, primeList.size());
		Assert.assertTrue(primeList.contains(0l));
		Assert.assertTrue(primeList.contains(1l));
		Assert.assertTrue(primeList.contains(2l));
		Assert.assertTrue(primeList.contains(3l));
		Assert.assertTrue(primeList.contains(5l));
		Assert.assertTrue(primeList.contains(7l));
		Assert.assertTrue(primeList.contains(11l));
		Assert.assertTrue(primeList.contains(13l));
		Assert.assertTrue(primeList.contains(17l));
		Assert.assertTrue(primeList.contains(19l));		
		
		Assert.assertFalse(primeList.contains(6l));		
		Assert.assertFalse(primeList.contains(9l));		
		Assert.assertFalse(primeList.contains(15l));		
		Assert.assertFalse(primeList.contains(16l));		
		
		List<Long> fpList = prime.getPrimesAsLongStream(20,false);
		Assert.assertEquals(primeList.size(), fpList.size());
		Assert.assertTrue(fpList.containsAll(primeList));
		Assert.assertTrue(primeList.containsAll(fpList));
		
		fpList = prime.getPrimesAsLongStream(20,true);
		Assert.assertEquals(primeList.size(), fpList.size());
		Assert.assertTrue(fpList.containsAll(primeList));
		Assert.assertTrue(primeList.containsAll(fpList));
	}
	
	@Test
	public void testGetPrimesAsLongStream() {
		
		long size = 8000;
		List<Long> primeList = getPrimes(size, false);
		List<Long> primeList2 = getPrimes(size, true);
		Assert.assertEquals(primeList.size(), primeList2.size());
		Assert.assertTrue(primeList.containsAll(primeList2));
	}
	
	private List<Long>  getPrimes(long size, boolean isParallel) {
		Prime prime = new Prime();		
		Instant start = Instant.now();
		List<Long> primeList = prime.getPrimesAsLongStream(size, isParallel);	
		Instant finish = Instant.now();
		long elapsed = Duration.between(start, finish).toMillis() /1000;
		System.out.println("primeList is " + primeList.size() + " took " + elapsed + " seconds, parallel is " + isParallel);
		
		return primeList;
	}
}
