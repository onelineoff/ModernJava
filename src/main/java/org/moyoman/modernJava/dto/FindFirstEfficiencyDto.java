package org.moyoman.modernJava.dto;

public class FindFirstEfficiencyDto {
	private int size;
	private long streamTimeMsec;
	private long setTimeMsec;
	private long tediousTimeMsec;
	
	public FindFirstEfficiencyDto() {		
	}
	
	public FindFirstEfficiencyDto(int size) {
		this.size = size;
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
	@Override
	public String toString() {
		return "FindFirstEfficiencyDto [size=" + size + ", streamTimeMsec=" + streamTimeMsec + ", setTimeMsec="
				+ setTimeMsec + ", tediousTimeMsec=" + tediousTimeMsec + "]";
	}
	
}
