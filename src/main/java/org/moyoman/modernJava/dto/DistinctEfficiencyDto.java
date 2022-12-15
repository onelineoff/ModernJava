package org.moyoman.modernJava.dto;

public class DistinctEfficiencyDto {
	private int size;
	private long streamTimeMsec;
	private long java7TimeMsec;
	
	public DistinctEfficiencyDto() {		
	}
	
	public DistinctEfficiencyDto(int size) {
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
	public long getJava7TimeMsec() {
		return java7TimeMsec;
	}
	public void setJava7TimeMsec(long java7TimeMsec) {
		this.java7TimeMsec = java7TimeMsec;
	}
	@Override
	public String toString() {
		return "DistinctEfficiencyDto [size=" + size + ", streamTimeMsec=" + streamTimeMsec + ", java7TimeMsec="
				+ java7TimeMsec + "]";
	}
}