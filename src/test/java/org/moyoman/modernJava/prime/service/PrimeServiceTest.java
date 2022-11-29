package org.moyoman.modernJava.prime.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.moyoman.modernJava.prime.service.PrimeService;
import org.moyoman.modernJava.util.MathUtils;
import org.moyoman.modernJava.util.MsecDuration;
import org.moyoman.modernJava.util.TestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PrimeServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(PrimeServiceTest.class);
	private static final Long FIRST_CANDIDATE = 3l;
	private static final Long LAST_CANDIDATE = 5000l;
	
	@Autowired
	private PrimeService primeService;
	
	@Test
	public void testGetPrimeList() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimes(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = MathUtils.xor(primeList);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);		
		TestUtils.logComputationResults("testGetPrimeList found {} primes, xor is {}, took {}.", 
				primeList.size(), xorValue, duration);
	}
	
	@Test
	public void testGetPrimesThroughStreams() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimesThroughStream(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = MathUtils.xor(primeList);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		TestUtils.logComputationResults("testGetPrimesThroughStreams found {} primes, xor is {}, took {}.", 
				primeList.size(), xorValue, duration);
	}
	
	@Test
	public void testGetPrimesThroughAlternateStreams() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimesThroughAlternateStreams(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = MathUtils.xor(primeList);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		TestUtils.logComputationResults("testGetPrimesThroughAlternateStreams found {} primes, xor is {}, took {}.", 
				primeList.size(), xorValue, duration);
	}
	
	@Test
	public void testGetPrimesThroughJava7Threading() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimesThroughJava7Threading(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = MathUtils.xor(primeList);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		TestUtils.logComputationResults("testGetPrimesThroughJava7Threading found {} primes, xor is {}, took {}.", 
				primeList.size(), xorValue, duration);
	}
}
