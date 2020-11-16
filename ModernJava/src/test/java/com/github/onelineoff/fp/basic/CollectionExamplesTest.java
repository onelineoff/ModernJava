package com.github.onelineoff.fp.basic;

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
}
