package org.devilry.core.daointerfaces;

import java.util.List;


public interface BaseNodeInterface {
	void setName(long nodeId, String name);
	String getName(long nodeId);
	void setDisplayName(long nodeId, String name);
	String getDisplayName(long nodeId);
	String getPath(long nodeId);

	List<Long> getChildren(long nodeId);

	void remove(long nodeId);
	boolean exists(long nodeId);

	long getNodeIdFromPath(String path);
	
	/** Get id of all administrators registered for the given node.
	 * 
	 * @param nodeId The unique number identifying an existing node.
	 * @return A list with the id of all administrators for the given node.
	 */
	List<Long> getAdmins(long nodeId);

	/** Check if a user is admin on the given node. */
	boolean isAdmin(long nodeId, long userId);

	/** Add a new administrator to the given node.
	 * 
	 * @param nodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	void addAdmin(long nodeId, long userId);

	/** Remove an administrator from the given node.
	 * 
	 * @param nodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	void removeAdmin(long nodeId, long userId);
}
