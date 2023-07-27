package org.moyoman.modernJava.util;

import org.junit.jupiter.api.Test;
import org.moyoman.modernJava.service.AuthorizationService;
import org.moyoman.modernJava.util.GenericUtils;
import org.junit.Assert;

public class TestGenericUtils {
	@Test
	public void testGetClient() { 
		GenericUtils genericUtils = new GenericUtils();
		
		String url = "http://localhost:8888/v1/mockEndpoints/createInfo/client/def";
		String client = genericUtils.getClient(url);
		Assert.assertEquals("def", client);
		
		url = "http://localhost:8888/v1/mockEndpoints/createInfo/" + AuthorizationService.TOP_LEVEL_COMPANY;
		client = genericUtils.getClient(url);
		Assert.assertEquals(AuthorizationService.TOP_LEVEL_COMPANY, client);
		
		url = "http://localhost:8888/v1/mockEndpoints/admin/createInfo/client/def";
		client = genericUtils.getClient(url);
		Assert.assertEquals("def", client);
		
		url = "http://localhost:8888/v1/mockEndpoints/admin/createInfo/" + AuthorizationService.TOP_LEVEL_COMPANY;
		client = genericUtils.getClient(url);
		Assert.assertEquals(AuthorizationService.TOP_LEVEL_COMPANY, client);
	}
}
