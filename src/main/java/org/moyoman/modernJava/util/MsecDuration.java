package org.moyoman.modernJava.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

public class MsecDuration {
	public int seconds;
	public int msec;
	
	public MsecDuration() {
		seconds = 0;
		msec = 0;
	}
	
	public MsecDuration(Instant i1, Instant i2) {
		this(Duration.between(i1, i2));
	}
	
	public MsecDuration(Duration duration) {
		seconds = (int) duration.getSeconds();
		msec = duration.getNano() / 1000000;
	}

	public int getSeconds() {
		return seconds;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getMsec() {
		return msec;
	}

	public void setMsec(int msec) {
		this.msec = msec;
	}

	@Override
	public int hashCode() {
		return Objects.hash(msec, seconds);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MsecDuration other = (MsecDuration) obj;
		return msec == other.msec && seconds == other.seconds;
	}
	
	@Override
	public String toString() {
		Float f = (float) seconds + ((float) msec) / 1000;
		String str = String.format("%.3f seconds", f);
		
		return str;
	}
}
