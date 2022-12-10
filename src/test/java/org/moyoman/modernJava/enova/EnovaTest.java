package org.moyoman.modernJava.enova;

import org.junit.Assert;
import org.junit.Test;
import org.moyoman.modernJava.enova.Enova;

public class EnovaTest {
//	@Test
//	public void testDoStuff() {
//		Enova enovaService = new Enova();
//		enovaService.doStuff();
//	}
	
	@Test
	public void testArrayManipulation() {
		Enova enovaService = new Enova();
		
		// Check empty parameter first.
		int moves = enovaService.arrayManipulation(null);
		Assert.assertEquals(0, moves);
		Integer[] arr = new Integer[0];
		moves = enovaService.arrayManipulation(arr);
		Assert.assertEquals(0, moves);
		
		Integer[] arr2 = {1,1,3,4,4,4};
		moves = enovaService.arrayManipulation(arr2);
		Assert.assertEquals(3, moves);
		
		Integer[] arr3 = {1,2,2,2,5,5,5,8};
		moves = enovaService.arrayManipulation(arr3);
		Assert.assertEquals(4, moves);
		
		Integer[] arr4 = {10,10,10};
		moves = enovaService.arrayManipulation(arr4);
		Assert.assertEquals(3, moves);
	}
	
	@Test
	public void testGetKey() {
		Enova enovaService = new Enova();
		Assert.assertEquals("99", enovaService.getIntToStringKey(9));
		Assert.assertEquals("14", enovaService.getIntToStringKey(1234));
		Assert.assertEquals("14", enovaService.getIntToStringKey(123456784));
		Assert.assertEquals("10", enovaService.getIntToStringKey(1203040));
	}
	
	@Test
	public void testGetSharedDigits() {
		Enova enovaService = new Enova();
		Assert.assertEquals(Enova.SENTINEL, enovaService.getMaxSharedDigits(null));
		int[] arr1 = {5};
		Assert.assertEquals(Enova.SENTINEL, enovaService.getMaxSharedDigits(arr1));
		int[] arr2 = {130,191,200,10};
		Assert.assertEquals(140, enovaService.getMaxSharedDigits(arr2).intValue());
		int[] arr3 = {405,45,300,300};
		Assert.assertEquals(600, enovaService.getMaxSharedDigits(arr3).intValue());
		int[] arr4 = {50,222,49,52,25};
		Assert.assertEquals(Enova.SENTINEL, enovaService.getMaxSharedDigits(arr4));
		int[] arr5 = {30,909,3190,993990,9009};
		Assert.assertEquals(9918, enovaService.getMaxSharedDigits(arr5).intValue());
		int[] arr6 = {1000000000,1000000000,1000000000,1000000000,1000000000};
		Assert.assertEquals(2000000000, enovaService.getMaxSharedDigits(arr6).intValue());
		
	}
}
