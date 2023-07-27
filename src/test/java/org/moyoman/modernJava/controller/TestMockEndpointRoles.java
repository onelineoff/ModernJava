package org.moyoman.modernJava.controller;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.moyoman.modernJava.domain.InternalRole;
import org.moyoman.modernJava.domain.InternalToken;
import org.moyoman.modernJava.service.LoginService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
/** Create users with different roles, and use the mock endpoints for testing authorization.
 *  These are very extensive tests, where read, modify, and admin users for both Tld,
 *  and 2 different clients, call all of the mock endpoints.
 *   
 *  The test show both that the appropriate level is required to have modify or admin access,
 *  Tld users have this access for any client, and clients only have it for their
 *  own company, not any other.
 */
public class TestMockEndpointRoles {
	@Autowired
	private LoginService loginService;
	
	private RestTemplate restTemplate;
	private InternalToken readAbcdToken;
	private InternalToken modifyAbcdToken;
	private InternalToken adminAbcdToken;
	private InternalToken readEfghToken;
	private InternalToken modifyEfghToken;
	private InternalToken adminEfghToken;
	private InternalToken readTldToken;
	private InternalToken modifyTldToken;
	private InternalToken adminTldToken;
	
	@LocalServerPort
	int port;
	
	@BeforeEach
	public void setup() {
		restTemplate = new RestTemplate();
		 		
		readAbcdToken = loginService.performLogin("read@abcd.com", LoginService.SECRET_PASSWORD);
		Assert.assertNotNull(readAbcdToken);
		modifyAbcdToken = loginService.performLogin("modify@abcd.com", LoginService.SECRET_PASSWORD);
		Assert.assertNotNull(modifyAbcdToken);
		adminAbcdToken = loginService.performLogin("admin@abcd.com", LoginService.SECRET_PASSWORD);
		Assert.assertNotNull(adminAbcdToken);
		readEfghToken = loginService.performLogin("read@efgh.com", LoginService.SECRET_PASSWORD);
		Assert.assertNotNull(readEfghToken);
		modifyEfghToken = loginService.performLogin("modify@efgh.com", LoginService.SECRET_PASSWORD);
		Assert.assertNotNull(modifyEfghToken);
		adminEfghToken = loginService.performLogin("admin@efgh.com", LoginService.SECRET_PASSWORD);
		Assert.assertNotNull(adminEfghToken);
		readTldToken = loginService.performLogin("read@moyoman.org", LoginService.SECRET_PASSWORD);
		Assert.assertNotNull(readTldToken);
		modifyTldToken = loginService.performLogin("modify@moyoman.org", LoginService.SECRET_PASSWORD);
		Assert.assertNotNull(modifyTldToken);
		adminTldToken = loginService.performLogin("admin@moyoman.org", LoginService.SECRET_PASSWORD);
		Assert.assertNotNull(adminTldToken);
	}

	/** Construct the url prefix using the autowired port.
	 * 
	 * @return The url prefix, e.g., http://localhost:55662/v1/mockEndpoints/
	 */
	private String getUrlPrefix() {
		return "http://localhost:" + port + "/v1/mockEndpoints/";
	}
	
	@Test
	public void testFullLifecycle() throws Exception {
		
		testReadAbcdClient();
		testModifyAbcdClient();
		testAdminAbcdClient();
		testReadEfghClient();
		testModifyEfghClient();
		testAdminEfghClient();
		testReadTld();
		testModifyTld();
		testAdminTld();
	}

	private void testReadAbcdClient() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", readAbcdToken.getTokenString());
		HttpEntity<String> httpEntity = new HttpEntity<>(" ", httpHeaders);
		executeTests(readAbcdToken, httpEntity, readAbcdToken.getOrg());
		executeTests(readAbcdToken, httpEntity, readEfghToken.getOrg());
		
	}

	private void testReadEfghClient() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", readEfghToken.getTokenString());
		HttpEntity<String> httpEntity = new HttpEntity<>(" ", httpHeaders);
		executeTests(readEfghToken, httpEntity, readAbcdToken.getOrg());
		executeTests(readEfghToken, httpEntity, readEfghToken.getOrg());
	}

	private void testModifyAbcdClient() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", modifyAbcdToken.getTokenString());
		HttpEntity<String> httpEntity = new HttpEntity<>(" ", httpHeaders);
		executeTests(modifyAbcdToken, httpEntity, modifyAbcdToken.getOrg());
		executeTests(modifyAbcdToken, httpEntity, modifyEfghToken.getOrg());
	}

	private void testModifyEfghClient() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", modifyEfghToken.getTokenString());
		HttpEntity<String> httpEntity = new HttpEntity<>(" ", httpHeaders);
		executeTests(modifyEfghToken, httpEntity, modifyAbcdToken.getOrg());
		executeTests(modifyEfghToken, httpEntity, modifyEfghToken.getOrg());
	}

	private void testAdminAbcdClient() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", adminAbcdToken.getTokenString());
		HttpEntity<String> httpEntity = new HttpEntity<>(" ", httpHeaders);
		executeTests(adminAbcdToken, httpEntity, adminAbcdToken.getOrg());
		executeTests(adminAbcdToken, httpEntity, adminEfghToken.getOrg());
	}

	private void testAdminEfghClient() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", adminEfghToken.getTokenString());
		HttpEntity<String> httpEntity = new HttpEntity<>(" ", httpHeaders);
		executeTests(adminEfghToken, httpEntity, adminAbcdToken.getOrg());
		executeTests(adminEfghToken, httpEntity, adminEfghToken.getOrg());
		
	}
	
	private void testReadTld() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", readTldToken.getTokenString());
		HttpEntity<String> httpEntity = new HttpEntity<>(" ", httpHeaders);
		executeTests(readTldToken, httpEntity, readTldToken.getOrg());
	}

	private void testModifyTld() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", modifyTldToken.getTokenString());
		HttpEntity<String> httpEntity = new HttpEntity<>(" ", httpHeaders);
		executeTests(modifyTldToken, httpEntity, modifyTldToken.getOrg());
	}
	
	private void testAdminTld() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", adminTldToken.getTokenString());
		HttpEntity<String> httpEntity = new HttpEntity<>(" ", httpHeaders);
		executeTests(adminTldToken, httpEntity, adminTldToken.getOrg());
	}
	
	private void executeTests(InternalToken user, HttpEntity<String> httpEntity, String clientApiPath) {
		executeAdminTldTests(user, httpEntity);
		executeModifyTldTests(user, httpEntity);
		executeReadOnlyTldTests(user, httpEntity);
		executeAdminClientTests(user, httpEntity, clientApiPath);
		executeModifyClientTests(user, httpEntity, clientApiPath);
		executeReadOnlyClientTests(user, httpEntity, clientApiPath);
	}
	
	private void executeAdminTldTests(InternalToken user, HttpEntity<String> httpEntity) {
		boolean isPermittedFlag = user.getRole() == InternalRole.ADMIN_TLD;
		
		String url = "admin/getInfo/tld";
		executeCall(url, HttpMethod.GET, httpEntity, isPermittedFlag);
		
		url = "admin/createInfo/tld";
		executeCall(url, HttpMethod.POST, httpEntity, isPermittedFlag);
		
		url = "admin/updateInfo/tld";
		executeCall(url, HttpMethod.PUT, httpEntity, isPermittedFlag);
		
		url = "admin/deleteInfo/tld/abcd";
		executeCall(url, HttpMethod.DELETE, httpEntity, isPermittedFlag);
	}

	private void executeModifyTldTests(InternalToken user, HttpEntity<String> httpEntity) {
		boolean isPermittedFlag = user.isTldUser() && user.canModify();
		
		String url =  "createInfo/tld";
		executeCall(url, HttpMethod.POST, httpEntity, isPermittedFlag);
		
		url =  "updateInfo/tld";
		executeCall(url, HttpMethod.PUT, httpEntity, isPermittedFlag);
		
		url =  "deleteInfo/tld/abcd";
		executeCall(url, HttpMethod.DELETE, httpEntity, isPermittedFlag);
	}

	private void executeReadOnlyTldTests(InternalToken user, HttpEntity<String> httpEntity) {
		String url =  "getInfo/tld";
		boolean isPermittedFlag = user.isTldUser();
		
		executeCall(url, HttpMethod.GET, httpEntity, isPermittedFlag);
	}

	private void executeAdminClientTests(InternalToken user, HttpEntity<String> httpEntity, String clientApiPath) {
		String client = user.getOrg();
		boolean clientMatches = client.equalsIgnoreCase(clientApiPath);
		boolean isPermittedFlag = user.isAdmin() && (user.isTldUser() || clientMatches);
			
		String url = "admin/getInfo/client/" + clientApiPath;
		executeCall(url, HttpMethod.GET, httpEntity, isPermittedFlag);
		
		url = "admin/createInfo/client/" + clientApiPath;
		executeCall(url, HttpMethod.POST, httpEntity, isPermittedFlag);
		
		url = "admin/updateInfo/client/" + clientApiPath;
		executeCall(url, HttpMethod.PUT, httpEntity, isPermittedFlag);
		
		url = "admin/deleteInfo/client/" + clientApiPath + "/1234";
		executeCall(url, HttpMethod.DELETE, httpEntity, isPermittedFlag);
	}

	private void executeModifyClientTests(InternalToken user, HttpEntity<String> httpEntity, String clientApiPath) {
		String client = user.getOrg();
		boolean clientMatches = client.equalsIgnoreCase(clientApiPath);
		boolean isPermittedFlag = user.canModify() && (clientMatches || user.isTldUser());	
		
		String url = "createInfo/client/" + clientApiPath;
		executeCall(url, HttpMethod.POST, httpEntity, isPermittedFlag);
		
		url = "updateInfo/client/" + clientApiPath;
		executeCall(url, HttpMethod.PUT, httpEntity, isPermittedFlag);
		
		url = "deleteInfo/client/" + clientApiPath + "/5678";
		executeCall(url, HttpMethod.DELETE, httpEntity, isPermittedFlag);
		
		executeReadOnlyClientTests(user, httpEntity, clientApiPath);
	}

	private void executeReadOnlyClientTests(InternalToken user, HttpEntity<String> httpEntity, String clientApiPath) {
		String client = user.getOrg();
		boolean isPermittedFlag = user.isTldUser() || client.equalsIgnoreCase(clientApiPath);
		String url = "getInfo/client/" + clientApiPath;
		executeCall(url, HttpMethod.GET, httpEntity, isPermittedFlag);
		
	}
	
	private void executeCall(String url, HttpMethod method, HttpEntity<String> httpEntity, boolean isPermitted) {
		String fullUrl = getUrlPrefix() + url;
		
		try {
			ResponseEntity<String> response = restTemplate.exchange(fullUrl, method, httpEntity, String.class);
			int statusCode = response.getStatusCodeValue();
			if (isPermitted) {
				Assert.assertTrue(statusCode == 200 || statusCode == 204);
			}
			else {
				Assert.assertTrue(statusCode == 401);
			}
			
		} 
		catch (HttpClientErrorException hcee) {
			int statusCode = hcee.getRawStatusCode();
			if (isPermitted) {
				Assert.assertTrue(false);
			}
			else {
				Assert.assertTrue(statusCode == 401);
			}
		}
	}
	
}
