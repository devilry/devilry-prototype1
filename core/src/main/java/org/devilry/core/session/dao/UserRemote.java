package org.devilry.core.session.dao;

import java.util.List;

import javax.ejb.Remote;


@Remote
public interface UserRemote {
	
	/** Create a new user.
	 * 
	 * @param name The name of the user. Note that this does not have to be unique.
	 * @param email The email-address. Must be unique. 
	 * @param phoneNumber The phone number of the user.
	 * @return The unique number identifying the newly created user (userId).
	 */
	long create(String name, String email, String phoneNumber);
	

	/** Get the name of the given user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @return The name of the user.
	 */
	String getName(long userId);
	
	/** Get the email-address registered for the given user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @return Email address. 
	 */
	String getEmail(long userId);
	
	/** Get the phone number registered for the given user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @return Phone number.
	 */
	String getPhoneNumber(long userId);

	/** Set the name of a registered user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @param name The name of the user. Note that this does not have to be unique.
	 */
	void setName(long userId, String name);
	
	/** Set the email address of a registered user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @param email Email address.
	 */
	void setEmail(long userId, String email);
	
	/** Set the phone number of a registered user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @param phoneNumber Telephone number.
	 */
	void setPhoneNumber(long userId, String phoneNumber);
	
	/** Remove a user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 */
	void remove(long userId);

	/** Get all identities associated with a user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @return A list of identities. 
	 */
	List<String> getIdentities(long userId);

	/** Create a new identity and attach it to the given user.
	 * 
	 * @param userId The unique number identifying an existing user 
	 * @param identity An existing identity.
	 */
	void addIdentity(long userId, String identity);
	
	/** Remove the given identity.
	 * 
	 * Note that you can remove all identities from a user.
	 * 
	 * @param identity An existing identity.
	 */
	void removeIdentity(String identity);
	
	/** Find a user by identity.
	 *
	 * @param identity An existing identity.
	 * @return The user with the given identity.
	 */
	long findUser(String identity);

	/** Get all users registered in the system.
	 * 
	 * @return A list of user-identities.
	 * */
	List<Long> getUsers();
	
	/** Check if the given identity exists. */
	boolean identityExists(String identity);
	
	/** Check if a user with the given email-address exists. */
	boolean emailExists(String email);	
}
