package org.moyoman.modernJava.util;

import org.moyoman.modernJava.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Assorted utility methods.
 * 
 */
public class GenericUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(GenericUtils.class);
	
	/** Obtain the client from the url.
	 *  This could be either the top level company, or the client.
	 * @param url The full url.
	 * @return The name of the client.
	 */
	public String getClient(String url) {
		String client = null;
		if (url.contains(AuthorizationService.TOP_LEVEL_COMPANY)) {
			client = AuthorizationService.TOP_LEVEL_COMPANY;
		}
		else {
			String prefix = "/client/";
			int index = url.indexOf(prefix);
			index += prefix.length();
			int index2 = url.indexOf("/", index);
			if (index2 > 0) {
				client = url.substring(index, index2);
			}
			else {
				client = url.substring(index);
			}	
		}
		
		if (client == null) {
			LOGGER.warn("Couldn't determine client for {}", url);
			throw new RuntimeException();
		}
		
		return client;
	}
}
