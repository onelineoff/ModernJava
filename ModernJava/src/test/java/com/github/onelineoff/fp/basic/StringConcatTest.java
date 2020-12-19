package com.github.onelineoff.fp.basic;

import org.junit.Assert;
import org.junit.Test;

public class StringConcatTest {

	@Test
	/** Compare imperative and fp ways of iterating through a list.
	 *  
	 */
	public void testListIterators() {
		StringConcat concat = new StringConcat();
		String output = concat.getListEntriesOldStyle(concat.stringTestList);
		System.out.println(output);
		
		String output2 = concat.getListEntriesFpVersion1(concat.stringTestList);		
		String output3 = concat.getListEntriesFpMoreConciseVersion(concat.stringTestList);
		String output4 = concat.getListEntriesUsingStreams(concat.stringTestList);
		
		Assert.assertEquals(output, output2);
		Assert.assertEquals(output, output3);
		Assert.assertEquals(output, output4);
		
	}
	
	@Test
	public void testGetLine() {
		String input = "abcdefg";
		String expected = "abcdefg" + StringConcat.SEPARATOR;
		StringConcat concat = new StringConcat();
		Assert.assertEquals(expected, concat.getLine(input));
	}
	
	@Test
	public void testGetLineFromSb() {
		StringConcat concat = new StringConcat();
		String input1 = "#jEiFHAL";
		String input2 = "#jEiF" + StringConcat.SEPARATOR + "HAL";
		String input3 = input2 + StringConcat.SEPARATOR;
		StringBuilder sb = new StringBuilder(input1);
		Assert.assertEquals(input1, concat.getStringFromSb(sb));
		sb = new StringBuilder(input2);
		Assert.assertEquals(input2, concat.getStringFromSb(sb));
		sb = new StringBuilder(input3);
		Assert.assertEquals(input2, concat.getStringFromSb(sb));
		
		
	}
}
