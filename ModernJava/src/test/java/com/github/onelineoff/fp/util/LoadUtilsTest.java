package com.github.onelineoff.fp.util;

import org.junit.Assert;
import org.junit.Test;

public class LoadUtilsTest {

	@Test
	public void testIsPrime() {
		LoadUtils utils = new LoadUtils();
		Assert.assertTrue(utils.isPrime(0l));
		Assert.assertTrue(utils.isPrime(1l));
		Assert.assertTrue(utils.isPrime(2l));
		Assert.assertTrue(utils.isPrime(3l));		
		Assert.assertTrue(utils.isPrime(5l));
		Assert.assertTrue(utils.isPrime(13l));
		Assert.assertTrue(utils.isPrime(37l));
		Assert.assertTrue(utils.isPrime(97l));
		Assert.assertTrue(utils.isPrime(359l));
		
		Assert.assertFalse(utils.isPrime(4l));	
		Assert.assertFalse(utils.isPrime(361l));
		Assert.assertFalse(utils.isPrime(8192l));
		Assert.assertFalse(utils.isPrime(363l));
		Assert.assertFalse(utils.isPrime(729l));
	}
}
