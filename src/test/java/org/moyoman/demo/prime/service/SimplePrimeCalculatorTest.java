package org.moyoman.demo.prime.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.moyoman.demo.prime.service.SimplePrimeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimplePrimeCalculatorTest {

	@Autowired
	private SimplePrimeCalculator simplePrimeCalculator;

	@Test
	public void testGetNextPrime() {
		long nextPrime = simplePrimeCalculator.getNextPrime(2);
		Assert.assertEquals(3l, nextPrime);
		nextPrime = simplePrimeCalculator.getNextPrime(5);
		Assert.assertEquals(7l, nextPrime);
		nextPrime = simplePrimeCalculator.getNextPrime(36);
		Assert.assertEquals(37l, nextPrime);
		nextPrime = simplePrimeCalculator.getNextPrime(120);
		Assert.assertEquals(127l, nextPrime);
		nextPrime = simplePrimeCalculator.getNextPrime(773);
		Assert.assertEquals(787l, nextPrime);
	}
}
