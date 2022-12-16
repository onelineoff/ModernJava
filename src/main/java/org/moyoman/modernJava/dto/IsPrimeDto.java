package org.moyoman.modernJava.dto;

import java.util.Objects;

public class IsPrimeDto {
	private long input;
	private boolean isPrime;
	
	public IsPrimeDto() {
		
	}
	
	public IsPrimeDto(long input, boolean isPrime) {
		this.input = input;
		this.isPrime = isPrime;
	}

	public long getInput() {
		return input;
	}

	public void setInput(long input) {
		this.input = input;
	}

	public boolean isPrime() {
		return isPrime;
	}

	public void setPrime(boolean isPrime) {
		this.isPrime = isPrime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(input, isPrime);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IsPrimeDto other = (IsPrimeDto) obj;
		return input == other.input && isPrime == other.isPrime;
	}

	@Override
	public String toString() {
		return "IsPrimeDto [input=" + input + ", isPrime=" + isPrime + "]";
	}
}
