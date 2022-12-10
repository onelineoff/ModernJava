package org.moyoman.modernJava.prime.controller;

import java.util.List;

import org.moyoman.modernJava.prime.dto.IsPrimeDto;
import org.moyoman.modernJava.prime.dto.PrimeListDto;
import org.moyoman.modernJava.prime.service.PrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primes/")
/** Rest controller class for the prime service.
 *  This is for illustration purposes, not meant for anything useful.
 *  
 *  Among other things, probably need a limit on the range of primes to search for.
 */
public class PrimeApi {
	@Autowired
	private PrimeService primeService;
	
	@GetMapping(value="info")
	public ResponseEntity<String> getInfo() {
		return ResponseEntity.ok("Prime server running\n");
	}
	
	@GetMapping(value="isPrime/{value}", produces={"application/json", "application/xml"})
	public ResponseEntity<IsPrimeDto> isPrime(@PathVariable(name="value") long value) {
		boolean isPrime = primeService.isPrime(value);
		IsPrimeDto isPrimeDto = new IsPrimeDto(value, isPrime);
		return ResponseEntity.ok(isPrimeDto);
	}
	
	@GetMapping(value="getPrimes/{start}/{end}", produces={"application/json", "application/xml"})
	public ResponseEntity<PrimeListDto> getPrimes(
			@PathVariable(name="start") long startValue, @PathVariable(name="end") long endValue) {
		List<Long> primeList = primeService.getPrimesThroughParallelStream(startValue, endValue);
		PrimeListDto primeListDto = new PrimeListDto(startValue, endValue, primeList);
		return ResponseEntity.ok(primeListDto);
	}
}
