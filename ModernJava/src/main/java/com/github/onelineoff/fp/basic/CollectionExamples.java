package com.github.onelineoff.fp.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.github.onelineoff.fp.util.LoadUtils;

public class CollectionExamples {

	protected List<String> stringTestList = Arrays.asList("e1", "e4", "e2", "e3", "e5");
	protected String SEPARATOR = "-";
	
	/** Convert a list of strings into a string, using Java 7 style iterator.
	 * 
	 * @param list The list of strings to be combined.
	 * @return The concatenated string.
	 */
	public String getListEntriesOldStyle(List<String> list) {
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str);
			sb.append(SEPARATOR);
		}
		
		return getStringFromSb(sb);
	}
	
	/** Verbose FP version of iterating through a list.
	 * 
	 * @param list The list of strings to be combined.
	 * @return The concatenated string.
	 */
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
	
	/** Concise FP version of iterating through a list.
	 * 
	 * @param list The list of strings to be combined.
	 * @return The concatenated string.
	 */	
	public String getListEntriesFpConciseVersion(List<String> list) {
		StringBuilder sb = new StringBuilder();
		
		list.forEach((str) -> sb.append(getLine(str)));
		return getStringFromSb(sb);
	}
		
	public String getListEntriesUsingStreams(List<String> list) {
		return list.stream().map(name -> name).collect(Collectors.joining(SEPARATOR));
	}
	
	/** Format a single line from the argument.
	 *  This can be used by different methods to ensure common results.
	 * @param str The input string.
	 * @return The formatted string.
	 */
	public String getLine(String str) {
		return str + SEPARATOR;
	}
	
	/** Process the input, and generate a string.
	 * 
	 * @param sb The input data.
	 * @return A string from the input data, with the final separator removed.
	 */
	public String getStringFromSb(StringBuilder sb) {
		String retStr = sb.toString();
		if (retStr.endsWith(SEPARATOR))
			retStr = retStr.substring(0, retStr.length() - SEPARATOR.length());
		
		return retStr;
	}
	
	public List<Integer> getPrimes(int max) {
		List<Integer> inputList = new ArrayList<>();
		for (int i=0; i<=max; i++) {
			inputList.add(i);
		}
		
		LoadUtils utils = new LoadUtils();
		Predicate<Integer> isPrime = utils::isPrime;
		List<Integer> retList = inputList.stream().filter(isPrime).collect(Collectors.toList());
		return retList;
	}
}
