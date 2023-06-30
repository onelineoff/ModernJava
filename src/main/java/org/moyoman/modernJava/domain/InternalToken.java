package org.moyoman.modernJava.domain;

/** This represents our internal jwt token.
 *  Various fields from the token are stored explicitly here,
 *  so that the token doesn't have to be parsed each time it's used.
 *  
 *  Various adaptors can be written to read various external representations,
 *  and convert them into ours, or vice-versa.
 *
 */
public class InternalToken {
	public static final String TOP_LEVEL_DOMAIN = "moyoman.org";
	
	String tokenString;
	String tokenType;
	String user;
	String org;
	InternalRole role;
	long expTime;
	boolean isTldFlag;
	boolean isAdminFlag;
	boolean isModifyFlag;

	public InternalToken(String user, InternalRole role, String org, long expTime, String tokenString) {
		this.user = user;
		this.tokenType ="Jwt";
		this.role = role;
		this.org = org;
		this.expTime = expTime;
		this.tokenString = tokenString;
		switch(role) {
		case READ_ONLY_TLD:
		case MODIFY_TLD:
		case ADMIN_TLD:
			isTldFlag = true;
			break;
		default:
			isTldFlag = false;
			break;
		}
		
		switch(role) {
		case ADMIN_CLIENT:
		case ADMIN_TLD:
			isAdminFlag = true;
			break;
		default:
			isAdminFlag = false;
			break;
		}
		
		switch(role) {
		case MODIFY_TLD:
		case ADMIN_TLD:
		case MODIFY_CLIENT:
		case ADMIN_CLIENT:
			isModifyFlag = true;
			break;
		default:
			isModifyFlag = false;
			break;
		}
	}

	public String getTokenString() {
		return tokenString;
	}

	public String getTokenType() {
		return tokenType;
	}
	
	public String getUser() {
		return user;
	}

	public String getOrg() {
		return org;
	}

	public InternalRole getRole() {
		return role;
	}

	public long getExpTime() {
		return expTime;
	}

	public void setExpTime(long expTime) {
		this.expTime = expTime;
	}

	public boolean isTldUser() {
		return isTldFlag;
	}
	
	public boolean isAdmin() {
		return isAdminFlag;
	}
	
	
	public boolean canModify() {
		return isModifyFlag;
	}
	
}