package org.devilry.core.daointerfaces;

import java.util.List;

import org.devilry.core.UnauthorizedException;

public interface UserCommon {
	
	/** Create a new user.
	 * 
	 * @param name The name of the user. Note that this does not have to be unique.
	 * @param email The email-address. Must be unique. 
	 * @param phoneNumber The phone number of the user.
	 * @return The unique number identifying the newly created user (userId).
	 */
	long create(String name, String email, String phoneNumber) throws UnauthorizedException;
	

	/** Get the name of the given user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @return The name of the user.
	 */
	String getName(long userId) throws UnauthorizedException;
	
	/** Get the email-address registered for the given user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @return Email address. 
	 */
	String getEmail(long userId) throws UnauthorizedException;
	
	/** Get the phone number registered for the given user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @return Phone number.
	 */
	String getPhoneNumber(long userId) throws UnauthorizedException;

	/** Set the name of a registered user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @param name The name of the user. Note that this does not have to be unique.
	 */
	void setName(long userId, String name) throws UnauthorizedException;
	
	/** Set the email address of a registered user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @param email Email address.
	 */
	void setEmail(long userId, String email) throws UnauthorizedException;
	
	/** Set the phone number of a registered user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @param phoneNumber Telephone number.
	 */
	void setPhoneNumber(long userId, String phoneNumber) throws UnauthorizedException;
	
	/** Remove a user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 */
	void remove(long userId) throws UnauthorizedException;

	/** Get all identities associated with a user.
	 * 
	 * @param userId The unique number identifying an existing user.
	 * @return A list of identities. 
	 */
	List<String> getIdentities(long userId) throws UnauthorizedException;

	/** Create a new identity and attach it to the given user.
	 * 
	 * @param userId The unique number identifying an existing user 
	 * @param identity An existing identity.
	 */
	void addIdentity(long userId, String identity) throws UnauthorizedException;
	
	/** Remove the given identity.
	 * 
	 * Note that you can remove all identities from a user.
	 * 
	 * @param identity An existing identity.
	 */
	void removeIdentity(String identity) throws UnauthorizedException;
	
	/** Find a user by identity.
	 *
	 * @param identity An existing identity.
	 * @return The user with the given identity.
	 */
	long findUser(String identity) throws UnauthorizedException;

	/** Get all users registered in the system.
	 * 
	 * @return A list of user-identities.
	 * */
	List<Long> getUsers() throws UnauthorizedException;
	
	/** Check if the given identity exists. */
	boolean identityExists(String identity) throws UnauthorizedException;
	
	/** Check if a user with the given email-address exists. */
	boolean emailExists(String email) throws UnauthorizedException;
	
	/** Check if a user with the given userId exists. */
	boolean userExists(long userId) throws UnauthorizedException;
	
	/** Get the userId of the authenticated user. */
	long getAuthenticatedUser() throws UnauthorizedException;

	/** Get the identity of the authenticated user. */
	String getAuthenticatedIdentity() throws UnauthorizedException;

	/** Make a user a a SuperAdmin, or revoke their SuperAdmin rights.
	 *
	 * A SuperAdmin is a user with unrestricted access to the entire system.
	 * Only a SuperAdmin can change this flag, and a SuperAdmin cannot invoke
	 * this method with his/her own <em>userId</em>.
	 *
	 * @param userId The user-id of an existing user.
	 * @param isSuperAdmin true to make the given user a SuperAdmin, and false
	 *		to make the given user a <em>normal</em> user.
	 */
	void setIsSuperAdmin(long userId, boolean isSuperAdmin) throws UnauthorizedException;

	/** Check if a user is SuperAdmin.
	 *
	 * @return true if the given user is SuperAdmin, otherwise return
	 *		false.
	 */
	boolean isSuperAdmin(long userId) throws UnauthorizedException;

	/** Get a list of all superadmins.
	 * 
	 * @return List with the id of all SuperAdmins.
	 */
	List<Long> getSuperAdmins() throws UnauthorizedException;
}