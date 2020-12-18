package com.github.onelineoff.fp.basic;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LambdaExamples {

	public List<StringIntPair> countWordsOldStyle(List<String> inputList) {

		for (String input : inputList) {
			input = input.trim();
			String[] words = input.split("\\s");
			for (String word : words) {
				if (word.length() == 0)
					continue;
				
				word = word.toLowerCase();
				StringIntPair pair = StringIntPair.getByKey(word);
				pair.incrementValue();
			}
		}
		
		List<StringIntPair> countList = StringIntPair.getList();
		Collections.sort(countList);
		return countList;
	}
	
	public List<StringIntPair> countWordsFp(List<String> inputList) {
//		List<String> list = inputList.stream().flatMap(line -> new List(line.split("\\s"))).stream().map(String::toLowerCase).collect(Collectors.toList());
		return null;
	}
}
