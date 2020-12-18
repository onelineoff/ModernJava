package com.github.onelineoff.fp.basic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

public class LambdaExamplesTest {

	@Test
	public void testWordCount() throws Exception {
		List<String> lines = getResource("book.txt");
		LambdaExamples examples = new LambdaExamples();
		List<StringIntPair> countList = examples.countWordsOldStyle(lines);
		Assert.assertTrue(countList.size() > 10);
		System.out.println("List size is " + countList.size());
		for (int i=0; i<10; i++) {
			StringIntPair word = countList.get(i);
			System.out.println(word);
			Assert.assertTrue(word.getKey().length() > 0);
			Assert.assertTrue(word.getValue() > 10);
		}	
	}
	
	@Test
	public void testFpWordCount() {
		String input = "Hello, I am a very very large fish.";
		String input2 = "Hello, I am a very very small fish.";
		List<String> list = new ArrayList<>();
		list.add(input);
		list.add(input2);
		Object o = list.stream().map(line -> line.split("\\s")).collect(Collectors.toList());
		Object o2 = list.stream().flatMap(line -> Arrays.asList(line.split("\\s")).stream()).collect(Collectors.toList());
		System.out.println(o);
		System.out.println(o2);
		System.out.println("Moooo");
	}
	
	@Test
	public void testChars() {
		String input = "aBcDeFg";
//		input.chars().filter(String::isLowerCase).
//		System.out.println(input.chars());
	}
	
	public List<String> getResource(String fname) throws Exception {
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(fname);
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		List<String> list = new ArrayList<>();
		br.lines().forEach(line -> list.add(line));
		
		return list;
	}
}
