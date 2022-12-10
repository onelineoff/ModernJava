package org.moyoman.modernJava.prime.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrimeListDto {
	private long startValue;
	private long endValue;
	private List<Long> primeList;
	
	public PrimeListDto(long startValue, long endValue, List<Long> primeList) {
		this.startValue = startValue;
		this.endValue = endValue;
		this.primeList = primeList;
	}
}
