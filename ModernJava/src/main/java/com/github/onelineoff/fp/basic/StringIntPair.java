package com.github.onelineoff.fp.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Store key and key counter.
 * 
 *  This is used to implement an ordered map by value using Java 7 functionality.
 *  Perhaps there is a more clever way of doing this, but the emphasis is on the
 *  functional programming capabilities of Java 8, rather than enhancing this code.
 *
 */
public class StringIntPair implements Comparable<StringIntPair> {
	private String key;
	private Integer value;
	private static Map<String, StringIntPair> keyMap;
	
	static {
		keyMap = new HashMap<>();
	}
	
	public static StringIntPair getByKey(String key) {
		StringIntPair pair = keyMap.get(key);
		if (pair == null) {
			pair = new StringIntPair(key);
			keyMap.put(pair.getKey(), pair);
		}
			
		return pair;
	}
	
	public static List<StringIntPair> getList() {
		return new ArrayList<>(keyMap.values());
	}
	
	private StringIntPair(String key) {
		this.key = key;
		this.value = 0;
	}
	
	private StringIntPair(String key, Integer value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}

	public Integer getValue() {
		return value;
	}

	public void incrementValue() {
		value++;
	}

	@Override
	public int compareTo(StringIntPair o) {
		if (o == null || o.getValue() == null)
			return 1;
		
		int diff = o.value - this.value;
		return diff;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringIntPair other = (StringIntPair) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StringIntPair [key=" + key + ", value=" + value + "]";
	}
	
}
