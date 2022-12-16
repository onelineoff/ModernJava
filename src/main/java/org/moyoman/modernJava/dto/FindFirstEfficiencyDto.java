package org.moyoman.modernJava.dto;

public class FindFirstEfficiencyDto {
	private int size;
	private long streamTimeMsec;
	private long setTimeMsec;
	private long tediousTimeMsec;
	private String errorMessage;
	private boolean isSuccessful;
	
	public FindFirstEfficiencyDto() {		
		isSuccessful = true;
	}
	
	public FindFirstEfficiencyDto(int size) {
		this.size = size;
		this.isSuccessful = true;
	}

	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public long getStreamTimeMsec() {
		return streamTimeMsec;
	}
	public void setStreamTimeMsec(long streamTimeMsec) {
		this.streamTimeMsec = streamTimeMsec;
	}
	public long getSetTimeMsec() {
		return setTimeMsec;
	}
	public void setSetTimeMsec(long setTimeMsec) {
		this.setTimeMsec = setTimeMsec;
	}
	public long getTediousTimeMsec() {
		return tediousTimeMsec;
	}
	public void setTediousTimeMsec(long tediousTimeMsec) {
		this.tediousTimeMsec = tediousTimeMsec;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	@Override
	public String toString() {
		return "FindFirstEfficiencyDto [size=" + size + ", streamTimeMsec=" + streamTimeMsec + ", setTimeMsec="
				+ setTimeMsec + ", tediousTimeMsec=" + tediousTimeMsec + ", errorMessage=" + errorMessage
				+ ", isSuccessful=" + isSuccessful + "]";
	}
}
