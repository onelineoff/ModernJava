package org.moyoman.modernJava.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.moyoman.modernJava.auth.JwtTokenGenerator;
import org.moyoman.modernJava.domain.InternalRole;
import org.moyoman.modernJava.domain.InternalToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/v1/mockEndpoints/")
@SecurityRequirement(name = "bearerAuth")
/** Set up mock REST calls to test permissions.
 *  
 *  This is intended to be a proof of concept for using AOP with Spring Security.
 *  The api is structured as follows, with the first rule that matches being the relevant one.
 *  In all cases where the url path includes the client, a user with a client role must match the client of the url.
 *  The ADMIN_TLD role is authorized to execute ANY call.
 *  The ADMIN_CLIENT role is authorized to execute ANY call for the client with which it's associated.
 *  THE READ_ONLY_CLIENT and MODIFY_CLIENT roles can only operate on the client with which it's associated.
 *  
 *  1. /admin/method/tld/... 
 *     This is only authorized for the ADMIN_TLD role.
 *  2. /admin/method/client/{client}/... 
 *     This is authorized for the ADMIN_CLIENT, or ADMIN_TLD roles.
 *  3. /method/tld/...
 *     GET calls are authorized for any TLD role.  
 *     POST, PUT, or DELETE calls are authorized for MODIFY_TLD, or ADMIN_TLD.
 *  4. /method/client/{client}/...
 *     GET calls are authorized for any role associated with that client.  
 *     POST, PUT, or DELETE calls are authorized for MODIFY_CLIENT, or ADMIN_CLIENT roles associated with that client.
 *     
 */
public class MockEndpointApi {
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MockEndpointApi.class);
	
	@Operation(summary = "Testing method, can only be called by a user with the ADMIN_TLD role.")
	@GetMapping(value="admin/getInfo/tld")
	public ResponseEntity<String> adminGetTldInfo(HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.ok("admin/getInfo/tld");
	}
	
	@Operation(summary = "Testing method, can only be called by a user with the ADMIN_TLD role.")
	@PostMapping(value="admin/createInfo/tld")
	public ResponseEntity<String> adminCreateTldInfo(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody String input) {
		return ResponseEntity.ok("admin/createInfo/tld/" + input);
	}
	
	@Operation(summary = "Testing method, can only be called by a user with the ADMIN_TLD role.")
	@PutMapping(value="admin/updateInfo/tld")
	public ResponseEntity<String> adminUpdateTldInfo(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody String input) {
		return ResponseEntity.ok("admin/updateInfo/tld/" + input);
	}

	@Operation(summary = "Testing method, can only be called by a user with the ADMIN_TLD role.")
	@DeleteMapping(value="admin/deleteInfo/tld/{info}")
	public ResponseEntity<String> adminDeleteTldInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("info") String info) {
		return ResponseEntity.ok("admin/deleteInfo/tld/" + info);
	}

	@Operation(summary = "Testing method, can only be called by a user with the ADMIN_TLD role, "
			+ "or the ADMIN_CLIENT role where the client in the api path matches the org of the user.")
	@GetMapping(value="admin/getInfo/client/{client}")
	public ResponseEntity<String> adminGetClientInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("client") String client) {
		return ResponseEntity.ok("admin/getInfo/" + client);
	}

	@Operation(summary = "Testing method, can only be called by a user with the ADMIN_TLD role, "
			+ "or the ADMIN_CLIENT role where the client in the api path matches the org of the user.")
	@PostMapping(value="admin/createInfo/client/{client}")
	public ResponseEntity<String> adminCreateClientInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("client") String client, @RequestBody String input) {
		return ResponseEntity.ok("admin/createInfo/" + client + "/" + input);
	}

	@Operation(summary = "Testing method, can only be called by a user with the ADMIN_TLD role, "
			+ "or the ADMIN_CLIENT role where the client in the api path matches the org of the user.")
	@PutMapping(value="admin/updateInfo/client/{client}")
	public ResponseEntity<String> adminUpdateClientInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("client") String client, @RequestBody String input) {
		return ResponseEntity.ok("admin/updateInfo/" + client + "/" + input);
	}

	@Operation(summary = "Testing method, can only be called by a user with the ADMIN_TLD role, "
			+ "or the ADMIN_CLIENT role where the client in the api path matches the org of the user.")
	@DeleteMapping(value="admin/deleteInfo/client/{client}/{info}")
	public ResponseEntity<String> adminDeleteClientInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("client") String client, @PathVariable("info") String info) {
		return ResponseEntity.ok("admin/deleteInfo/" + client + "/" + info);
	}

	@Operation(summary = "Testing method, can only be called by a user with one of the "
			+ "READ_ONLY_TLD, MODIFY_TLD, or ADMIN_TLD role.")
	@GetMapping(value="getInfo/tld")
	public ResponseEntity<String> getTldInfo(HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.ok("getInfo/tld");
	}

	@Operation(summary = "Testing method, can only be called by a user with the "
			+ "MODIFY_TLD, or ADMIN_TLD role.")
	@PostMapping(value="createInfo/tld")
	public ResponseEntity<String> createTldInfo(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody String input) {
		return ResponseEntity.ok("createInfo/tld/" + input);
	}
	
	@Operation(summary = "Testing method, can only be called by a user with the "
			+ "MODIFY_TLD, or ADMIN_TLD role.")
	@PutMapping(value="updateInfo/tld")
	public ResponseEntity<String> updateTldInfo(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody String input) {
		return ResponseEntity.ok("updateInfo/tld/" + input);
	}
	
	@Operation(summary = "Testing method, can only be called by a user with the "
			+ "MODIFY_TLD, or ADMIN_TLD role.")
	@DeleteMapping(value="deleteInfo/tld/{info}")
	public ResponseEntity<String> deleteTldInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("info") String info) {
		return ResponseEntity.ok("deleteInfo/tld/" + info);
	}

	@Operation(summary = "Testing method, can be called by any Tld role, "
			+ "or any client role where the client in the api path matches the org of the user.")
	@GetMapping(value="getInfo/client/{client}")
	public ResponseEntity<String> getClientInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("client") String client) {
		return ResponseEntity.ok("getInfo/" + client);
	}

	@Operation(summary = "Testing method, can be called by a user with the MODIFY_TLD, or ADMIN_TLD roles,"
			+ "or by a MODIFY_CLIENT or ADMIN_CLIENT role where the client in the api path matches the org of the user.")
	@PostMapping(value="createInfo/client/{client}")
	public ResponseEntity<String> createClientInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("client") String client, @RequestBody String input) {
		return ResponseEntity.ok("createInfo/" + client + "/" + input);
	}

	@Operation(summary = "Testing method, can be called by a user with the MODIFY_TLD, or ADMIN_TLD roles,"
			+ "or by a MODIFY_CLIENT or ADMIN_CLIENT role where the client in the api path matches the org of the user.")
	@PutMapping(value="updateInfo/client/{client}")
	public ResponseEntity<String> updateClientInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("client") String client, @RequestBody String input) {
		return ResponseEntity.ok("updateInfo/" + client + "/" + input);
	}

	@Operation(summary = "Testing method, can be called by a user with the MODIFY_TLD, or ADMIN_TLD roles,"
			+ "or by a MODIFY_CLIENT or ADMIN_CLIENT role where the client in the api path matches the org of the user.")
	@DeleteMapping(value="deleteInfo/client/{client}/{info}")
	public ResponseEntity<String> deleteClientInfo(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("client") String client, @PathVariable("info") String info) {
		return ResponseEntity.ok("deleteInfo/" + client + "/" + info);
	}
}
