package org.devilry.core.daointerfaces;

import java.util.List;


public interface BaseNodeInterface {
	void setName(long baseNodeId, String name);
	String getName(long baseNodeId);
	void setDisplayName(long baseNodeId, String name);
	String getDisplayName(long baseNodeId);

	void remove(long baseNodeId);
	boolean exists(long baseNodeId);

	/** Get path from id. */
	String getPath(long baseNodeId);
	
	/** Get id from path. */
	long getIdFromPath(String path);
	
	/** Get id of all administrators registered for the given node.
	 * 
	 * @param baseNodeId The unique number identifying an existing node.
	 * @return A list with the id of all administrators for the given node.
	 */
	List<Long> getAdmins(long baseNodeId);

	/** Check if a user is admin on the given node. */
	boolean isAdmin(long baseNodeId, long userId);

	/** Add a new administrator to the given node.
	 * 
	 * @param baseNodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	void addAdmin(long baseNodeId, long userId);

	/** Remove an administrator from the given node.
	 * 
	 * @param baseNodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	void removeAdmin(long baseNodeId, long userId);
}
