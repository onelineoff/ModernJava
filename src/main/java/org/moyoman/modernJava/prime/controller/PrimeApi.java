package org.moyoman.modernJava.prime.controller;


import org.moyoman.modernJava.prime.service.PrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primes/")
public class PrimeApi {
	@Autowired
	private PrimeService primeService;
	
	@GetMapping(value="info")
	public ResponseEntity<String> getInfo() {
		return ResponseEntity.ok("Prime server running\n");
	}
}
