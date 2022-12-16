package org.moyoman.modernJava.dto;

import java.util.List;
import java.util.Objects;

public class PrimeListDto {
	private long startValue;
	private long endValue;
	private List<Long> primeList;
	
	public PrimeListDto(long startValue, long endValue, List<Long> primeList) {
		this.startValue = startValue;
		this.endValue = endValue;
		this.primeList = primeList;
	}

	public long getStartValue() {
		return startValue;
	}

	public void setStartValue(long startValue) {
		this.startValue = startValue;
	}

	public long getEndValue() {
		return endValue;
	}

	public void setEndValue(long endValue) {
		this.endValue = endValue;
	}

	public List<Long> getPrimeList() {
		return primeList;
	}

	public void setPrimeList(List<Long> primeList) {
		this.primeList = primeList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(endValue, primeList, startValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrimeListDto other = (PrimeListDto) obj;
		return endValue == other.endValue && Objects.equals(primeList, other.primeList)
				&& startValue == other.startValue;
	}

	@Override
	public String toString() {
		return "PrimeListDto [startValue=" + startValue + ", endValue=" + endValue + ", primeList=" + primeList + "]";
	}
	
}
