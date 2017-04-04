package com.db.security;

/**
 * @author Josh Long
 */
@SuppressWarnings("serial")
// tag::code[]
class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(String userId) {
		super("could not find user '" + userId + "'.");
	}
}