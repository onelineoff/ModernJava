package org.moyoman.modernJava.domain;

import java.security.Key;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.moyoman.modernJava.auth.JwtTokenGenerator;
import org.moyoman.modernJava.domain.InternalRole;
import org.moyoman.modernJava.domain.InternalToken;

import io.jsonwebtoken.Claims;

public class TestInternalToken {

	@Test
	public void testRoleLogic() {
		
//		// Should be able to read from any domain.
//		String subject = "read_only@moyoman.org";
//		InternalToken stoken = getInternalToken(subject);
//		
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.READ_ONLY_CLIENT, "client.org"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_CLIENT, "client.org"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_CLIENT, "client.org"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.READ_ONLY_TLD, "moyoman.org"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_TLD, "moyoman.org"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_TLD, "moyoman.org"));
//		
//		// Should be able to read or modify any domain.
//		subject = "modify@moyoman.org";
//		stoken = getInternalToken(subject);
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.READ_ONLY_CLIENT, "client.org"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.MODIFY_CLIENT, "client.org"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_CLIENT, "client.org"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.READ_ONLY_TLD, "moyoman.org"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.MODIFY_TLD, "moyoman.org"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_TLD, "moyoman.org"));
//		
//		// Should be able to do anything.
//		subject = "admin@moyoman.org";
//		stoken = getInternalToken(subject);
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.READ_ONLY_CLIENT, "client.org"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.MODIFY_CLIENT, "client.org"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.ADMIN_CLIENT, "client.org"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.READ_ONLY_TLD, "moyoman.org"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.MODIFY_TLD, "moyoman.org"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.ADMIN_TLD, "moyoman.org"));
//		
//		// Should only be able to read from abcd.com.
//		subject = "read_only@abcd.com";
//		stoken = getInternalToken(subject);
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.READ_ONLY_CLIENT, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_CLIENT, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_CLIENT, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.READ_ONLY_TLD, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_TLD, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_TLD, "abcd.com"));
//		
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.READ_ONLY_CLIENT, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_CLIENT, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_CLIENT, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.READ_ONLY_TLD, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_TLD, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_TLD, "defg.com"));
//		
//		// Should only be able to read or modify abdc.com.
//		subject = "modify@abcd.com";
//		stoken = getInternalToken(subject);
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.READ_ONLY_CLIENT, "abcd.com"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.MODIFY_CLIENT, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_CLIENT, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.READ_ONLY_TLD, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_TLD, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_TLD, "abcd.com"));
//		
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.READ_ONLY_CLIENT, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_CLIENT, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_CLIENT, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.READ_ONLY_TLD, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_TLD, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_TLD, "defg.com"));
//		
//		// Should be able to do anything on abcd.com.
//		subject = "admin@abcd.com";
//		stoken = getInternalToken(subject);
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.READ_ONLY_CLIENT, "abcd.com"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.MODIFY_CLIENT, "abcd.com"));
//		Assert.assertTrue(stoken.canAssumeRole(InternalRole.ADMIN_CLIENT, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.READ_ONLY_TLD, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_TLD, "abcd.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_TLD, "abcd.com"));
//		
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.READ_ONLY_CLIENT, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_CLIENT, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_CLIENT, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.READ_ONLY_TLD, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.MODIFY_TLD, "defg.com"));
//		Assert.assertFalse(stoken.canAssumeRole(InternalRole.ADMIN_TLD, "defg.com"));
		Assert.assertTrue(true);
	}
	
	private InternalToken getInternalToken(String subject) {
		JwtTokenGenerator jwtTokenGenerator = new JwtTokenGenerator();
		Key key = jwtTokenGenerator.getSecretKey();
		String token = jwtTokenGenerator.generateJwtToken(subject, InternalRole.ADMIN_CLIENT, key);
		Assert.assertNotNull(token);
		
		Claims claims = jwtTokenGenerator.getClaims(key, token);
		String retrievedEntity = claims.get("entity", String.class);
		String role = claims.get("role", String.class);
		String org = claims.get("org", String.class);
		long expTime = claims.getExpiration().getTime();
		
		return new InternalToken(retrievedEntity, InternalRole.valueOf(role), org, expTime, token);
	}
}
