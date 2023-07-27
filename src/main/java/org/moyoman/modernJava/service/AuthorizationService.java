package org.moyoman.modernJava.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.moyoman.modernJava.domain.InternalRole;
import org.moyoman.modernJava.domain.InternalToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
/** Verify that specific users are authenticated, or authorized to make specific API calls.
 *  These checks are done based on an implicit api scheme.
 *  
 *  The class level path are part of the authentication check.
 *  API calls starting with one of the paths in the openUrlSet are public, and don't require authentication.
 *  API calls not starting with one of the paths in the validUrlSet are rejected without any further checking.
 *  
 *  The first two or three levels of the method level path are part of the authorization check.
 *  An admin call is of the form: admin/<method>/client/<org>/...
 *  If org corresponds to the TOP_LEVEL_COMPANY, then only the ADMIN_TLD role is authorized to make this call.
 *  If org is a different client, then either ADMIN_CLIENT or ADMIN_TLD are authorized, but for the former,
 *  the user has to belong to the same organization as the one in the API call.
 *  
 *  Other calls are of the form: <method>/client/<org>/...
 *  If org corresponds to the TOP_LEVEL_COMPANY:
 *  For GET calls, any of the _TLD roles are authorized.
 *  For POST, PUT, and DELETE calls, either MODIFY_TLD, or ADMIN_TLD are authorized.
 *  
 *  If org is a different client, then:
 *  For GET calls, any _TLD role is authorized, and any _CLIENT role is authorized, 
 *  as long as the org of the caller corresponds to the org of the api.
 *  For POST, PUT, and DELETE calls, either MODIFY_TLD, or ADMIN_TLD are authorized,
 *  or MODIFY_CLIENT or ADMIN_CLIENT are authorized,
 *  as long as the org of the caller corresponds to the org of the api.
 */
public class AuthorizationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);
	
	/** API calls which don't require authentication.*/
	private static Set<String> openUrlSet = new HashSet<>();
	/** API calls which are valid, but require authentication and authorization.*/
	private static Set<String> validUrlSet = new HashSet<>();
	
	/** The path in the api that indicates the top level company.  This is case sensitive.*/
	public static String TOP_LEVEL_COMPANY = "moyoman.org";
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	static {
		openUrlSet.add("/v1/auth/"); // The login and refresh methods.
		openUrlSet.add("/v3/api-docs"); // Needed to create the swagger3 (OpenAPI) ui page.
		openUrlSet.add("/numeric"); // Don't need auth, make it simple to call.
		openUrlSet.add("/prime"); // Don't need auth, make it simple to call.
		
		validUrlSet.add("/v1/mockEndpoints/");
		// TODO Add the "real" endpoints to the validUrlSet.
	}
	
	/** Check if authentication is required to call this endpoint.
	 *  
	 * @param fullApiPath The API endpoint being checked.
	 * @return true if it's open, or false if authentication is required.
	 */
	public boolean isOpen(String fullApiPath) {
		boolean isAuthorized = false;
		for (String url : openUrlSet) {
			if (fullApiPath.startsWith(url)) {
				isAuthorized = true;
				break;
			}
		}
		
		return isAuthorized;
	}
	
	/** Check if the top level part of the api is valid.
	 *  TODO: This implementation could be expanded so that the valid endpoints
	 *  are retrieved from some external source.
	 *  This would allow for new functionality to be controlled with feature flags.
	 *  
	 * @param fullApiPath The API endpoint being checked.
	 * @return true if this is a valid endpoint, or false otherwise.
	 */
	public boolean isAuthorizedTopLevelEndpoint(String fullApiPath) {
		boolean isAuthorized = false;
		if (isOpen(fullApiPath)) {
			LOGGER.info("Calling open endpoint from isAuthorized({}), returning true", fullApiPath);
			return true;
		}
		
		for (String url : validUrlSet) {
			if (fullApiPath.startsWith(url)) {
				isAuthorized = true;
				break;
			}
		}
		
		if (!isAuthorized) {
			LOGGER.warn("Calling api which is neither open, nor valid {}", fullApiPath);
		}
		
		return isAuthorized;
	}

	/** Validate that the given user can call this method.
	 * 
	 * @param httpMethod The HTTP verb.
	 * @param client The client from the api path.
	 * @param fullApiPath The full api path, e.g., /v1/auth/login.
	 * @param token The token containing the relevant information about the caller.
	 * @return true if the call should be allowed to proceed, or false.
	 */
	public boolean isAuthorizedForUser(HttpMethod httpMethod, String client, String fullApiPath, InternalToken token) {
		boolean isAuthorized = token.getExpTime() > new Date().getTime();

		if (! isAuthorized)
		{
			LOGGER.warn("Jwt token has expired for {}", token.getUser());
		}
		else {
			if (isAuthorizedTopLevelEndpoint(fullApiPath)) {

				if (isAuthorized) {
					String relativeApiPath = getRelativeApiPath(fullApiPath);
					Set<InternalRole> validRoles = getValidRoles(httpMethod, relativeApiPath);
					isAuthorized = validRoles.contains(token.getRole());

					LOGGER.debug("full path is {}, client is {}, valid roles are {}", fullApiPath, client, validRoles);
					if (! isAuthorized) {
						LOGGER.warn("User {} is authenticated, but not authorized to call {} with role {}", token.getUser(),
								fullApiPath, token.getRole());
					}
				}
				
				if (!token.isTldUser() && !client.equalsIgnoreCase(token.getOrg())) {
					LOGGER.warn("Mismatch for user org {} and url org {}", token.getOrg(), client);
					isAuthorized = false;
				}
			}
			else {
				isAuthorized = false;
			}
		}

		return isAuthorized;
	}
	
	/** Get the relative api path from the full path.
	 *  So, /v1/auth/login would return /login.
	 * 
	 * @param fullApiPath The api path being checked.
	 * @return The relative api path.
	 * @throws IllegalArgumentException - Thrown if the full path is not valid.
	 */
	protected String getRelativeApiPath(String fullApiPath) {
		String relativeApiPath = null;
		for (String curr : validUrlSet) {
			if (fullApiPath.startsWith(curr)) {
				relativeApiPath = fullApiPath.substring(curr.length());
			}
		}
		
		if (relativeApiPath == null) {
			LOGGER.warn("Api call {} is not valid", fullApiPath);
			throw  new IllegalArgumentException();
		}
		
		return relativeApiPath;
	}
	
	/** Determine the roles that are authorized to call this url.
	 *  This requires that the following api naming convention is followed.
	 *  
	 *  The top level of the api is /version/path, e.g., /v1/auth.
	 *  If this is in the openUrlSet, then it's authorized without further checks.
	 *  If it's in the validUrlSet, then the caller must provide valid credentials.
	 *  If it's not in the validUrlSet, then it's denied without further checks.
	 *  
	 *  The relative api path is either:
	 *  admin/{method}/client/{client}, or
	 *  /{method}/client/{client}
	 *  
	 *  Calls in the admin path must have an admin role.
	 *  Calls not in the admin path can have any role for GET calls, and must have a MODIFY_ or ADMIN_ role for POST, PUT, or DELETE calls.
	 *  
	 *  If the client is the TOP_LEVEL_COMPANY, then only the _TLD roles are valid, where if it's a client,
	 *  then either _CLIENT or _TLD roles are valid.
	 *  
	 *  For api calls with a {client} component, the caller must belong to the same organization as the client parameter in the path.
	 *  That check is NOT done here.
	 *  
	 *  The caller of this method can do that check either before or after this one.
	 *  
	 *  An enhancement that could be made would be to create a new annotation, such as GenericRole,
	 *  which takes a parameter of Set<InternalRole>.  This would explicitly set authorized
	 *  roles.  If the GenericRole annotation is present, that would be used.  Otherwise, the
	 *  naming convention above would still be followed, which means that the existing code would not
	 *  have to be rewritten, and the GenericRole annotations would only need to be added to the
	 *  desired methods, not all of them.
	 *  
	 *  @param httpMethod - The REST method, e.g, GET.
	 *  @param relativeApiPath The relative api path, e.g., getStuff/company1.
	 * 
	 *  @return A set containing 0 or more roles authorized to make this api call.
	 */
	protected Set<InternalRole> getValidRoles(HttpMethod httpMethod, String relativeApiPath) {
		Set<InternalRole> authorizedSet = new HashSet<>();

		// To prevent accidental security issues by putting the company at the wrong level of the api path,
		// if moyoman.org appears anywhere in the api path, only a _TLD role will be authorized.
		boolean isTopLevelCompany = relativeApiPath.toLowerCase().contains(TOP_LEVEL_COMPANY);
		
		String[] pathArgs = relativeApiPath.split("/");
		if (pathArgs[0].equalsIgnoreCase("admin")) {
			authorizedSet.add(InternalRole.ADMIN_TLD);
			if (!isTopLevelCompany) {
				authorizedSet.add(InternalRole.ADMIN_CLIENT);
			}
		}
		else {
			switch(httpMethod) {			
			case GET: 		
				authorizedSet.add(InternalRole.READ_ONLY_TLD);
				authorizedSet.add(InternalRole.MODIFY_TLD);
				authorizedSet.add(InternalRole.ADMIN_TLD);	
				
				if (! isTopLevelCompany) {
					authorizedSet.add(InternalRole.READ_ONLY_CLIENT);				
					authorizedSet.add(InternalRole.MODIFY_CLIENT);
					authorizedSet.add(InternalRole.ADMIN_CLIENT);
				}
				break;
			case POST:
			case PUT:
			case DELETE:				
				authorizedSet.add(InternalRole.MODIFY_TLD);
				authorizedSet.add(InternalRole.ADMIN_TLD);
				
				if (! isTopLevelCompany) {
					authorizedSet.add(InternalRole.MODIFY_CLIENT);
					authorizedSet.add(InternalRole.ADMIN_CLIENT);
				}
				break;
			default:
				LOGGER.warn("Unsupported http method {}", httpMethod);
				break;
			}
		}		
		return authorizedSet;
	}

}
