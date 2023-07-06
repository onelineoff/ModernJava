package org.moyoman.modernJava.fp;

import java.util.List;
import java.util.function.Predicate;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestFAJ {
	@Test
	public void testIsNotNull() {	
		FAJ faj = new FAJ();
		Assert.assertTrue(faj.isNotNull.test("abcd"));
		Assert.assertFalse(faj.isNotNull.test(null));
	}
	
	@Test
	public void testAlternateForms() {
		FAJ faj = new FAJ();
		String[] inputArr = {"abcd", "efgh", "", null};

		for (String input : inputArr) {	
			Assert.assertEquals(faj.isNotNull.test(input), faj.isNotNullV1.test(input));
			Assert.assertEquals(faj.isNotNull.test(input), faj.isNotNullV2.test(input));
			Assert.assertEquals(faj.isNotNull.test(input), faj.isNotNullV3.test(input));
		}
	}

	@Test
	public void testFuncIsNotNull() {
		FAJ faj = new FAJ();
		String[] inputArr = {"abcd", "efgh", "", null};

		for (String input : inputArr) {	
		Assert.assertEquals(faj.isNotNull.test(input), faj.funcIsNotNull.apply(input));
		Assert.assertEquals(faj.isNotNull.test(input), faj.funcIsNotNull.apply(input));
		Assert.assertEquals(faj.isNotNull.test(input), faj.funcIsNotNull.apply(input));
		}
	}

	@Test
	public void testConciselyPassingFunctions() {
		FAJ faj = new FAJ();
		List<Predicate<String>> plist = List.of(faj.isNotNullV1, faj.isNotNullV2, faj.isNotNullV3);
		String[] inputArr = {"abcd", "efgh", "", null};

		for (String input : inputArr) {		
			for (Predicate<String> p : plist) {
				Assert.assertEquals(faj.isNotNull.test(input), p.test(input));
			}
		}
	}
}
