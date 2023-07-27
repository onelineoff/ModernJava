package org.moyoman.modernJava.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/** Set up security using Spring AOP.
 *  NOT using Spring Security, as it's difficult (for me) to set up,
 *  and not providing any additional value.
 * 
 *  The basic authorization/authentication works as follows:
 *  The /v1/auth calls are open.
 *  The user logs in by calling /v1/auth/login, with a json body with the username/password.
 *  The jwt token returned is then used as an Authorization header in all subsequent calls.
 *  
 *  The token expires, so the /v1/auth/refresh endpoint can be called to get a new token.
 *  
 *  Currently, only the /v1/mockEndpoints urls can be called.
 *  Calls to any other endpoints will be refused.
 *  
 *  The /v1/mockEndpoints urls are for test/demo purposes.
 *  The real endpoints will be added with the same pattern as those,
 *  and they can then be enabled in the aop configuration.
 *
 */
@Configuration
@EnableAspectJAutoProxy
public class SecurityConfiguration {
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	
	/** Create support for adding authentication to the OpenApi/Swagger 3 ui.
	 * 
	 * @return The Open API bean.
	 */
	@Bean
	public OpenAPI customizeOpenAPI() {
	    final String securitySchemeName = "bearerAuth";
	    return new OpenAPI()
	      .addSecurityItem(new SecurityRequirement()
	        .addList(securitySchemeName))
	      .components(new Components()
	        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
	          .name(securitySchemeName)
	          .type(SecurityScheme.Type.HTTP)
	          .scheme("bearer")
	          .bearerFormat("JWT")));
	    }
	
}
