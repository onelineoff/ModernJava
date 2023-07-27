package org.moyoman.modernJava.service;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.moyoman.modernJava.domain.InternalRole;
import org.moyoman.modernJava.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;

@SpringBootTest
public class TestAuthorizationService {
	@Autowired
	private AuthorizationService authorizationService;
	
	@Test
	public void testOpenEndpoints() {
		Assert.assertTrue(authorizationService.isOpen("/v1/auth/login/"));
		Assert.assertTrue(authorizationService.isOpen("/v1/auth/refresh/"));
		Assert.assertTrue(authorizationService.isAuthorizedTopLevelEndpoint("/v1/auth/login/"));
		Assert.assertTrue(authorizationService.isAuthorizedTopLevelEndpoint("/v1/auth/refresh/"));
		Assert.assertFalse(authorizationService.isOpen("/v1/mockEndpoints/login/"));
	}
		
	@Test
	public void testValidEndpoints() {
		Assert.assertTrue(authorizationService.isAuthorizedTopLevelEndpoint("/v1/auth/refresh/"));
		Assert.assertTrue(authorizationService.isAuthorizedTopLevelEndpoint("/v1/mockEndpoints/abcd"));
		Assert.assertTrue(authorizationService.isAuthorizedTopLevelEndpoint("/v1/mockEndpoints/abcd/efgh/1/2/3"));
		Assert.assertFalse(authorizationService.isAuthorizedTopLevelEndpoint("/v1/provision/abcd/efgh/1/2/3"));
	}
	
	@Test
	public void testGetValidRolesForNonAdminEndpoints() {
		Set<InternalRole> expectedAllRolesSet = new HashSet<>();
		expectedAllRolesSet.add(InternalRole.READ_ONLY_CLIENT);
		expectedAllRolesSet.add(InternalRole.MODIFY_CLIENT);
		expectedAllRolesSet.add(InternalRole.ADMIN_CLIENT);
		expectedAllRolesSet.add(InternalRole.READ_ONLY_TLD);
		expectedAllRolesSet.add(InternalRole.MODIFY_TLD);
		expectedAllRolesSet.add(InternalRole.ADMIN_TLD);

		Set<InternalRole> expectedAllTLCRolesSet = new HashSet<>();
		expectedAllTLCRolesSet.add(InternalRole.READ_ONLY_TLD);
		expectedAllTLCRolesSet.add(InternalRole.MODIFY_TLD);
		expectedAllTLCRolesSet.add(InternalRole.ADMIN_TLD);
		
		Set<InternalRole> expectedModifyRolesSet = new HashSet<>();
		expectedModifyRolesSet.add(InternalRole.MODIFY_CLIENT);
		expectedModifyRolesSet.add(InternalRole.ADMIN_CLIENT);
		expectedModifyRolesSet.add(InternalRole.MODIFY_TLD);
		expectedModifyRolesSet.add(InternalRole.ADMIN_TLD);
		
		Set<InternalRole> expectedModifyTLCRolesSet = new HashSet<>();
		expectedModifyTLCRolesSet.add(InternalRole.MODIFY_TLD);
		expectedModifyTLCRolesSet.add(InternalRole.ADMIN_TLD);
		
		String url = "getStuff/abcd/id/1234";
		testGetValidRoles(url, HttpMethod.GET, expectedAllRolesSet);
				
		url = "getStuff/" + AuthorizationService.TOP_LEVEL_COMPANY;
		testGetValidRoles(url, HttpMethod.GET, expectedAllTLCRolesSet);
		
		url = "postStuff/abcd";
		testGetValidRoles(url, HttpMethod.POST, expectedModifyRolesSet);
		
		url = "postStuff/" + AuthorizationService.TOP_LEVEL_COMPANY;
		testGetValidRoles(url, HttpMethod.POST, expectedModifyTLCRolesSet);
		
		url = "puttStuff/abcd/more/stuff/here";
		testGetValidRoles(url, HttpMethod.PUT, expectedModifyRolesSet);
		
		url = "puttStuff/" + AuthorizationService.TOP_LEVEL_COMPANY + "/more/stuff/here";
		testGetValidRoles(url, HttpMethod.PUT, expectedModifyTLCRolesSet);
		
		url = "deleteStuff/efghi/val/6543";
		testGetValidRoles(url, HttpMethod.DELETE, expectedModifyRolesSet);
		
		url = "deleteStuff/" + AuthorizationService.TOP_LEVEL_COMPANY + "/val/6543";
		testGetValidRoles(url, HttpMethod.DELETE, expectedModifyTLCRolesSet);
	}
	
	@Test
	public void testGetValidRolesForAdminEndpoints() {
		Set<InternalRole> expectedClientRoleSet = new HashSet<>();
		expectedClientRoleSet.add(InternalRole.ADMIN_CLIENT);
		expectedClientRoleSet.add(InternalRole.ADMIN_TLD);
		
		Set<InternalRole> expectedTLCRoleSet = new HashSet<>();
		expectedTLCRoleSet.add(InternalRole.ADMIN_TLD);
		
		String url = "admin/getStuff/abcd/id/1234";
		
		testGetValidRoles(url, HttpMethod.GET, expectedClientRoleSet);
				
		url = "admin/getStuff/" + AuthorizationService.TOP_LEVEL_COMPANY;		
		testGetValidRoles(url, HttpMethod.GET, expectedTLCRoleSet);
		
		url = "admin/postStuff/abcd";		
		testGetValidRoles(url, HttpMethod.POST, expectedClientRoleSet);
		
		url = "admin/postStuff/" + AuthorizationService.TOP_LEVEL_COMPANY;		
		testGetValidRoles(url, HttpMethod.POST, expectedTLCRoleSet);
		
		url = "admin/deleteStuff/c1/id/4567";		
		testGetValidRoles(url, HttpMethod.PUT, expectedClientRoleSet);
		
		url = "admin/puttStuff/" + AuthorizationService.TOP_LEVEL_COMPANY + "/more/stuff/here";		
		testGetValidRoles(url, HttpMethod.PUT, expectedTLCRoleSet);
		
		url = "admin/deleteStuff/kiiiefjei/val/6543";		
		testGetValidRoles(url, HttpMethod.DELETE, expectedClientRoleSet);
		
		url = "admin/delettteStuff/" + AuthorizationService.TOP_LEVEL_COMPANY + "/more/stuff/here";		
		testGetValidRoles(url, HttpMethod.DELETE, expectedTLCRoleSet);
	}
	
	private void testGetValidRoles(String url, HttpMethod httpMethod, Set<InternalRole> expectedRoles) {
		Set<InternalRole>  validRoleSet = authorizationService.getValidRoles(httpMethod, url);
		Assert.assertEquals(expectedRoles, validRoleSet);
	}
}
