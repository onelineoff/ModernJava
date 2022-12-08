package org.moyoman.modernJava.prime.service;

import java.time.Instant;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.moyoman.modernJava.prime.service.PrimeService;
import org.moyoman.modernJava.util.MathUtils;
import org.moyoman.modernJava.util.MsecDuration;
import org.moyoman.modernJava.util.TestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
/** Test the relative efficiency of various methods of finding primes in a given range.
 *  Note that these tests don't verify the correctness of the results, just the fact
 *  that they all return the same results.
 */
public class PrimeServiceTest {
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
		TestUtils.logComputationResults("testGetPrimeList", primeList.size(), xorValue, duration);
	}
	
	@Test
	public void testGetPrimesThroughSequentialStream() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimesThroughSequentialStream(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = MathUtils.xor(primeList);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		TestUtils.logComputationResults("testGetPrimesThroughSequentialStream", primeList.size(), xorValue, duration);
	}
	
	@Test
	public void testGetPrimesThroughParallelStream() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimesThroughParallelStream(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = MathUtils.xor(primeList);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		TestUtils.logComputationResults("testGetPrimesThroughParallelStream", primeList.size(), xorValue, duration);
	}
	
	@Test
	public void testGetPrimesThroughAlternateParallelStream() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimesThroughAlternateParallelStream(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = MathUtils.xor(primeList);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		TestUtils.logComputationResults("testGetPrimesThroughAlternateParallelStream", primeList.size(), xorValue, duration);
	}
	
	@Test
	public void testGetPrimesThroughJava7Threading() {
		Instant start = Instant.now();
		List<Long> primeList = primeService.getPrimesThroughJava7Threading(FIRST_CANDIDATE, LAST_CANDIDATE);
		Long xorValue = MathUtils.xor(primeList);
		Instant end = Instant.now();
		MsecDuration duration = new MsecDuration(start, end);
		TestUtils.logComputationResults("testGetPrimesThroughJava7Threading", primeList.size(), xorValue, duration);
	}
}
