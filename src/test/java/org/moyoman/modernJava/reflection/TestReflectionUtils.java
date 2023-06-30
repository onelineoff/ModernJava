package org.moyoman.modernJava.reflection;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestReflectionUtils {
	@Test
	public void testGetAnnotations() throws Exception {
		ReflectionUtils reflectionUtils = new ReflectionUtils();
		String className = "org.moyoman.modernJava.controller.MockEndpointApi";
		String apiMethod = "getClientInfo";
		String path = reflectionUtils.getApiPathFromMethod(className, apiMethod);
		Assert.assertEquals("/v1/mockEndpoints/getInfo/client/{client}", path);
	}
}
