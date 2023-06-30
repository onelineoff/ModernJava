package org.moyoman.modernJava.domain;

/** Contains the information necessary to verify a hashed password.
 *  This is an immutable class, there is no reason to modify any of the
 *  fields once it's created.
 *
 */
public class Credentials {
	private String username;
	private String hashedPassword;
	private String salt;
	private String algorithm;
	
	public Credentials(String username, String hashedPassword, String salt, String algorithm) {
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.salt = salt;
		this.algorithm = algorithm;
	}
	
	public String getUsername() {
		return username;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public String getSalt() {
		return salt;
	}

	public String getAlgorithm() {
		return algorithm;
	}
}
