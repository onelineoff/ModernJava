package org.moyoman.modernJava.prime.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.moyoman.modernJava.prime.service.PrimeService;
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
		Long xorValue = getXorValue(primeList);
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		LOGGER.info("testGetPrimeList found {} primes, xor is {}, took {} seconds", 
				primeList.size(), xorValue, duration.getSeconds());
	}
	
	@Test
	public void testGetPrimesThroughStreams() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimesThroughStream(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = getXorValue(primeList);
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		LOGGER.info("testGetPrimesThroughStreams found {} primes, xor is {}, took {} seconds", 
				primeList.size(), xorValue, duration.getSeconds());
	}
	
	@Test
	public void testGetPrimesThroughAlternateStreams() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimesThroughAlternateStreams(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = getXorValue(primeList);
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		LOGGER.info("testGetPrimesThroughAlternateStreams found {} primes, xor is {}, took {} seconds", 
				primeList.size(), xorValue, duration.getSeconds());
	}
	
	@Test
	public void testGetPrimesThroughJava7Threading() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimesThroughJava7Threading(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = getXorValue(primeList);
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
		LOGGER.info("testGetPrimesThroughJava7Threading found {} primes, xor is {}, took {} seconds", 
				primeList.size(), xorValue, duration.getSeconds());
	}
	
	// TODO Make this a utility method, write unit test.
	private Long getXorValue(List<Long> list) {
		return list.stream().reduce((i, j) -> i ^ j).get().longValue();
	}
}
