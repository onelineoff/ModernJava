package com.github.onelineoff.fp.basic;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/** Various examples of string concatenation.
 *  The idea is to take a list of a, b, c, d, e, and output: a-b-c-d-e
 *  Start with the Java 7 version, and progressively simplify using Java 8 FP.
 *
 */
public class StringConcat {

	protected List<String> stringTestList = Arrays.asList("e1", "e4", "e2", "e3", "e5");
	protected static String SEPARATOR = "-";
	
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
	
	/** More concise FP version of iterating through a list.
	 * 
	 * @param list The list of strings to be combined.
	 * @return The concatenated string.
	 */	
	public String getListEntriesFpMoreConciseVersion(List<String> list) {
		StringBuilder sb = new StringBuilder();
		
		list.forEach((str) -> sb.append(getLine(str)));
		return getStringFromSb(sb);
	}
		
	/** Simplest version using FP.
	 *  Note that this is the only version that doesn't have to call getStringFromSb() 
	 *  to strip the extraneous separator at the end of the string.
	 * 
	 * @param list The list of strings to be combined.
	 * @return The concatenated string.
	 */
	public String getListEntriesUsingStreams(List<String> list) {
		return list.stream().map(name -> name).collect(Collectors.joining(SEPARATOR));
	}
	
	/** Format a single line from the argument.
	 *  This can be used by different methods to ensure common results.
	 *  Note that this method is not necessary for the simplest FP implementation.
	 *  
	 * @param str The input string.
	 * @return The formatted string.
	 */
	public String getLine(String str) {
		return str + SEPARATOR;
	}
	
	/** Process the input, and generate a string.
	 * Note that this method is not necessary for the simplest FP implementation.
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
	
}
