package com.github.onelineoff.fp.util;

import org.junit.Assert;
import org.junit.Test;

public class LoadUtilsTest {

	@Test
	public void testIsPrime() {
		LoadUtils utils = new LoadUtils();
		Assert.assertTrue(utils.isPrime(0));
		Assert.assertTrue(utils.isPrime(1));
		Assert.assertTrue(utils.isPrime(2));
		Assert.assertTrue(utils.isPrime(3));
		Assert.assertFalse(utils.isPrime(4));
		Assert.assertTrue(utils.isPrime(5));
		Assert.assertTrue(utils.isPrime(13));
		Assert.assertTrue(utils.isPrime(37));
		Assert.assertTrue(utils.isPrime(97));
		Assert.assertTrue(utils.isPrime(359));
		Assert.assertFalse(utils.isPrime(8192));
		Assert.assertFalse(utils.isPrime(363));
		Assert.assertFalse(utils.isPrime(729));
	}
}
