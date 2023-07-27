package org.moyoman.modernJava.domain;

import org.bouncycastle.crypto.params.Argon2Parameters;

/** Configuration parameters for generating the hash value using the Argon2 algorithm.
 *  Ideally, it will take about 1 second to generate the hash.
 *  
 *  TODO: The iterations, memory limit, and parallelism should be externalized,
 *  since the optimal values for these are very much dependent on the deployment environment.
 *  
 *  @see https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html
 *
 */
public class Argon2Credentials extends Credentials {

	public static final int ARGON2_TYPE = Argon2Parameters.ARGON2_id;
	public static final int ARGON2_VERSION = Argon2Parameters.ARGON2_VERSION_13;
	public static final int ARGON2_ITERATIONS = 4; // TODO: Way too low, maybe 8 - 12. For faster testing.
	public static final int ARGON2_MEM_LIMIT = 65536;
	public static final int ARGON2_OUTPUT_LENGTH = 128;
	public static final int ARGON2_BASE64_OUTPUT_LENGTH = 172;
	public static final int ARGON2_PARALLELISM = 10;
	public static final int ARGON2_SALT_LENGTH = 128;
	public static final int ARGON2_BASE64_SALT_LENGTH = 172;

	public Argon2Credentials(String username, String hashedPassword, String salt) {
		super(username, hashedPassword, salt, "Argon2");
	}
	
	

}
