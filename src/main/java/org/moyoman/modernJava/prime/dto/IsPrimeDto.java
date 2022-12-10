package org.moyoman.modernJava.prime.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IsPrimeDto {
	private long input;
	private boolean isPrime;
	
	public IsPrimeDto(long input, boolean isPrime) {
		this.input = input;
		this.isPrime = isPrime;
	}
}
