package com.github.onelineoff.fp.basic;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class CollectionExamples {

	protected List<String> stringTestList = Arrays.asList("e1", "e4", "e2", "e3", "e5");
	protected String SEPARATOR = "-";
	
	public String getListEntriesOldStyle(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str);
			sb.append(SEPARATOR);
		}
		
		return getStringFromSb(sb);
	}
	
	public String getListEntriesFpVersion1(List<String> list) {
		StringBuilder sb = new StringBuilder();
		
		list.forEach(new Consumer<String>() {
			public void accept(final String name) {
				sb.append(name);
				sb.append(SEPARATOR);
			}
		});
		
		return getStringFromSb(sb);
	}
	
	public String getListEntriesFpRealVersion(List<String> list) {
		StringBuilder sb = new StringBuilder();
		
		list.forEach((str) -> sb.append(getLine(str)));
		return getStringFromSb(sb);
	}
		
	public String getLine(String str) {
		return str + SEPARATOR;
	}
	
	public String getStringFromSb(StringBuilder sb) {
		String retStr = sb.toString();
		if (retStr.endsWith(SEPARATOR))
			retStr = retStr.substring(0, retStr.length() - SEPARATOR.length());
		
		return retStr;
	}
}
