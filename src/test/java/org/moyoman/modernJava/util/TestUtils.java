package org.moyoman.modernJava.util;

import java.time.Instant;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Class containing various utility methods for testing.
 */
public class TestUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestUtils.class);
	private static final Random random = new Random(Instant.now().getEpochSecond());
	
	public static int getRandomInt(int max) {
		return random.nextInt(max + 1);
	}
	
}
