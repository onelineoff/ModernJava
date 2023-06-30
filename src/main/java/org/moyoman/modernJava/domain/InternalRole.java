package org.moyoman.modernJava.domain;

/** The various roles an authenticated user can assume.
 *  The client roles can only operate on their own 
 * orgs, while a top level domain (TLD) user can operate on any org.
 * 
 * For a given client, modify can do read or modify, and admin can do admin, modify or read for their own client.
 * For a TLD role, the same is true, except that this can be applied for any client.
 * 
 * In the future, more fine grained roles could be added, although probably not in this enum.
 */
public enum InternalRole {
	READ_ONLY_CLIENT, 
	MODIFY_CLIENT, 
	ADMIN_CLIENT, 
	READ_ONLY_TLD, 
	MODIFY_TLD, 
	ADMIN_TLD;
}
