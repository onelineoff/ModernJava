package org.moyoman.modernJava.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.moyoman.modernJava.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestLoginService {
	@Autowired
	private LoginService loginService;
	
	@Test
	public void testSuccessfulLogin() {
		String token = loginService.performLogin("onelineoff@moyoman.org", LoginService.SECRET_PASSWORD).getTokenString();
		System.out.println(token);
	}
	
	@Test
	public void testUnsuccessfulLogin() {
		try {
			loginService.performLogin("onelineoff@moyoman.org", "abcdefg");
			Assert.fail();
		}
		catch(IllegalArgumentException iae) {
			// Expected, invalid password
		}
	}
}
