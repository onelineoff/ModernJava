package com.github.onelineoff.fp.basic;

import org.junit.Assert;
import org.junit.Test;

public class CollectionExamplesTest {

	@Test
	public void testListIterators() {
		CollectionExamples examples = new CollectionExamples();
		String output = examples.getListEntriesOldStyle(examples.stringTestList);
		System.out.println(output);
		
		String output2 = examples.getListEntriesFpVersion1(examples.stringTestList);
		
		String output3 = examples.getListEntriesFpRealVersion(examples.stringTestList);
		
		Assert.assertEquals(output, output2);
		Assert.assertEquals(output, output3);
		
	}
}
