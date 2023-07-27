package org.moyoman.modernJava.auth;

import java.net.URL;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.moyoman.modernJava.domain.InternalToken;
import org.moyoman.modernJava.service.AuthorizationService;
import org.moyoman.modernJava.util.GenericUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Aspect
@Component
/** Intercept REST calls, and reject them if not authenticated or authorized.
 *  This is using generic Spring AOP, NOT Spring Security.
 *  
 *  Currently, Get, Post, Put, and Delete calls are supported.
 *  If they are public, then no further checking is done.
 *  If they are not valid top level endpoints, they are rejected.
 *  If they are valid calls, the user is checked to see that they are authenticated,
 *  and that they have the proper role for the endpoint they're calling.
 *  
 *  The required role for an endpoint is based on a convention that is used here.
 *  At some point in the future, explicit annotations for individual methods may be added
 *  for more fine grained permissions.
 *  
 *  Check the README file for a detailed explanation of the implicit authorization scheme.
 */
public class JwtSecurityFilter {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtSecurityFilter.class);
	
	@Autowired
	private JwtTokenGenerator jwtTokenGenerator;
	@Autowired
	private AuthorizationService authorizationService;	
	private GenericUtils genericUtils;
	
	public JwtSecurityFilter() {
		genericUtils = new GenericUtils();
	}
	
	@Before("@annotation(org.springframework.web.bind.annotation.GetMapping)")
	/** Filter all GET REST calls.
	 * 
	 * @param joinPoint AOP information about the method being called.
	 * @throws Throwable Thrown if the authentication or authorization fails.
	 */
	public void filterGet(JoinPoint joinPoint) throws Throwable {
		filterForMethod(joinPoint, HttpMethod.GET);
	}
	
	@Before("@annotation(org.springframework.web.bind.annotation.PostMapping)")
	/** Filter all POST REST calls.
	 * 
	 * @param joinPoint AOP information about the method being called.
	 * @throws Throwable Thrown if the authentication or authorization fails.
	 */
	public void filterPost(JoinPoint joinPoint)
			throws Throwable {
		filterForMethod(joinPoint, HttpMethod.POST);
	}
	
	@Before("@annotation(org.springframework.web.bind.annotation.PutMapping)")
	/** Filter all PUT REST calls.
	 * 
	 * @param joinPoint AOP information about the method being called.
	 * @throws Throwable Thrown if the authentication or authorization fails.
	 */
	public void filterPut(JoinPoint joinPoint)
			throws Throwable {
		filterForMethod(joinPoint, HttpMethod.PUT);
	}
	
	@Before("@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
	/** Filter all DELETE REST calls.
	 * 
	 * @param joinPoint AOP information about the method being called.
	 * @throws Throwable Thrown if the authentication or authorization fails.
	 */
	public void filterDelete(JoinPoint joinPoint)
			throws Throwable {
		filterForMethod(joinPoint, HttpMethod.DELETE);
	}
	
	/** Perform the actual authentication and authorization checks.
	 *  No action is taken other than logging for a valid call.
	 *  
	 * @param joinPoint AOP information about the method being called.
	 * @param method One of GET, POST, PUT, or DELETE.
	 * @throws Throwable Thrown if the authentication or authorization fails.
	 */
	protected void filterForMethod(JoinPoint joinPoint, HttpMethod method) throws Throwable {
		HttpServletRequest request = null;
		Object[] args = joinPoint.getArgs();
	
		for (Object arg : args) {
			if (arg instanceof HttpServletRequest) {
				request = (HttpServletRequest) arg;
				LOGGER.debug("Request url is {}", request.getRequestURL().toString());
			}
		}
		
		String url = request.getRequestURL().toString();
		URL netUrl = new URL(url);
		
		// The full path would be something like /v1/auth/login.
		String fullPath = netUrl.getPath();
		LOGGER.debug("Interceptor for {} request for {}", method, fullPath);
		
		// Certain endpoints can be called by anyone, for example, the login endpoint.
		if (authorizationService.isOpen(fullPath)) {
			LOGGER.debug("Calling open endpoint {}, returning without further checks", fullPath);
			return;
		}		
		
		// Get the client from the url, either the tld, or an external client.
		String client = genericUtils.getClient(url);
		
		// Either of these methods will fail if the user does not have a valid token, 
		// or if they are not authorized to make the given call.
		InternalToken token = getToken(joinPoint);
		verifyAuthorization(token, client, fullPath, method);
	}

	/** Get the jwt token from the join point.
	 * If it can't be obtained an http status code of 401 .
	 * 
	 * @param joinPoint The AOP arguments.
	 * @return The internal token.
	 * @throws ResponseStatusException An exception if a valid token can't be created.
	 */
	protected InternalToken getToken(JoinPoint joinPoint) throws ResponseStatusException {
		HttpServletRequest request = null;
		
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			if (arg instanceof HttpServletRequest) {
				request = (HttpServletRequest) arg;
				LOGGER.debug("Request url is {}", request.getRequestURL().toString());
			}
		}

		if (request == null) {
			LOGGER.warn("Called filter interceptor with no HttpServletRequest arg ");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		
		LOGGER.debug("Attempt authentication from interceptor");

		String jwtToken = request.getHeader("Authorization");
		if (jwtToken == null) {
			LOGGER.warn("Failed to obtain jwt token from Authorization header.");
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}

		InternalToken token = jwtTokenGenerator.getInternalToken(jwtToken);
		return token;
	}
	
	/** Determine if the user is authorized to make the given call, log the attempt, and throw an exception if not.
	 * 
	 * @param token The token
	 * @param client The client in the api call.
	 * @param fullPath The api endpoint.
	 * @param method Either GET, POST, PUT, or DELETE.
	 * @throws ResponseStatusException Thrown if the user is not authenticated or authorized to make this call.
	 */
	protected void verifyAuthorization(InternalToken token, String client, String fullPath, HttpMethod method) throws ResponseStatusException {
		if (authorizationService.isAuthorizedForUser(method, client, fullPath, token)) {
			LOGGER.debug("User {} authorized for call to {}", token.getUser(), fullPath);
		}
		else {
			LOGGER.warn("Failed to authorize user {} for call to {}", token.getUser(), fullPath);
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
	}
}
