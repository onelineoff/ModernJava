package com.github.onelineoff.fp.basic;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CollectionExamplesTest {

	@Test
	/** Compare imperative and fp ways of iterating through a list.
	 *  
	 */
	public void testListIterators() {
		CollectionExamples examples = new CollectionExamples();
		String output = examples.getListEntriesOldStyle(examples.stringTestList);
		System.out.println(output);
		
		String output2 = examples.getListEntriesFpVersion1(examples.stringTestList);		
		String output3 = examples.getListEntriesFpConciseVersion(examples.stringTestList);
		String output4 = examples.getListEntriesUsingStreams(examples.stringTestList);
		
		Assert.assertEquals(output, output2);
		Assert.assertEquals(output, output3);
		Assert.assertEquals(output, output4);
		
	}
	
	@Test
	public void testGetPrimes() {
		CollectionExamples examples = new CollectionExamples();
		List<Integer> primes = examples.getPrimes(20);
		Assert.assertEquals(10, primes.size());
		Assert.assertTrue(primes.contains(0));
		Assert.assertTrue(primes.contains(1));
		Assert.assertTrue(primes.contains(2));
		Assert.assertTrue(primes.contains(3));
		Assert.assertTrue(primes.contains(5));
		Assert.assertTrue(primes.contains(7));
		Assert.assertTrue(primes.contains(11));
		Assert.assertTrue(primes.contains(13));
		Assert.assertTrue(primes.contains(17));
		Assert.assertTrue(primes.contains(19));		
	}
}
