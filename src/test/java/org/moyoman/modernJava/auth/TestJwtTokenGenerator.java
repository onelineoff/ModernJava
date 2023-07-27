package org.moyoman.modernJava.auth;

import java.security.Key;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.moyoman.modernJava.auth.JwtTokenGenerator;
import org.moyoman.modernJava.domain.InternalRole;
import org.moyoman.modernJava.domain.InternalToken;

import io.jsonwebtoken.Claims;

public class TestJwtTokenGenerator {
	
	@Test
	public void testGenerateAndVerifyToken() {
		JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();
		String subject = "onelineoff@moyoman.org";

		Key key = jwtTokenGenerator.getSecretKey();
		String token = jwtTokenGenerator.generateJwtToken(subject, InternalRole.READ_ONLY_TLD, key);
		System.out.println(token);
		Assert.assertNotNull(token);
		
		Claims claims = jwtTokenGenerator.getClaims(key, token);
		String retrievedEntity = claims.get("entity", String.class);
		String role = claims.get("role", String.class);
		String org = claims.get("org", String.class);
		
		Assert.assertEquals(subject, retrievedEntity);
		Assert.assertEquals(role, InternalRole.READ_ONLY_TLD.name());
		Assert.assertEquals(org, InternalToken.TOP_LEVEL_DOMAIN);
		
	}
	
	@Test
	public void generateToken() {
		JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();
		String subject = "read_only@abcd.com";

		Key key = jwtTokenGenerator.getSecretKey();
		String token = jwtTokenGenerator.generateJwtToken(subject, InternalRole.READ_ONLY_CLIENT, key);
		System.out.println(token);
		Assert.assertNotNull(token);
	}
}
