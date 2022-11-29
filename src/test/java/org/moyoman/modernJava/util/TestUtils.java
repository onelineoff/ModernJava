package org.moyoman.modernJava.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Class containing various utility methods for testing.
 */
public class TestUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(TestUtils.class);
	
	public static void logComputationResults(String logStr, Object... args) {
		LOGGER.info(logStr, args);
	}
}
