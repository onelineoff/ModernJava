package org.moyoman.modernJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

@SpringBootTest
public class MiscServiceTest {
	@Autowired
	private MiscService miscService;
	
	@Test
	public void testIsValid() {
		String str = "1234.56";
		Assert.assertTrue(miscService.isDouble(str));
	}
	
	@Test
	/** Test in a single threaded fashion.
	 * 
	 */
	public void testRunningAverage() {
		int totalValues = 999;
		RunningAverage ra = new RunningAverage();
		for (int i=1; i<= totalValues; i++) {
			ra.add(i);
		}
		
		Assert.assertTrue(Math.abs(500.0 - ra.getAverage()) < 0.000001);
	}
	
	@Test
	/** Test in a multi threaded fashion.
	 * 
	 */
	public void testMultiThreadedRunningAverage() { 
		int totalThreads = 999;
		RunningAverage ra = new RunningAverage();
		ExecutorService es = Executors.newFixedThreadPool(totalThreads);
		HashSet<SharedRunningAverage> set = new HashSet<>();
		
		for (int i=1; i<= totalThreads; i++) {
			set.add(new SharedRunningAverage(ra, i));
		}
		
		try {
		es.invokeAll((Collection<? extends Callable<SharedRunningAverage>>) set);
		}
		catch(Exception e) {
			System.out.println("so sad");
		}
		
		Assert.assertTrue(Math.abs(500.0 - ra.getAverage()) < 0.000001);
	}

}
