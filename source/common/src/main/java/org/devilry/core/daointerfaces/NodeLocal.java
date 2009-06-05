package org.devilry.core.daointerfaces;

import java.util.List;

public interface NodeLocal extends BaseNodeInterface {
	long create(String name, String displayName);
	long create(String name, String displayName, long parentId);

	long getParent(long nodeId);

	List<Long> getChildnodes(long nodeId);
	List<Long> getChildcourses(long nodeId);

	/** Get a list of all nodes without a parent-node.
	 * 
	 * @return List of node-ids.
	 */
	List<Long> getToplevelNodes();

	/** Get a list of nodes where the authenticated user is admin.
	 * 
	 * @return List of user-ids.
	 */
	List<Long> getNodesWhereIsAdmin();
	
	/** 
	 * Add a new administrator to the given node.
	 * @param nodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	public void addNodeAdmin(long nodeId, long userId);
	
	
	/** 
	 * Remove an administrator from the given node.
	 * @param nodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	public void removeNodeAdmin(long nodeId, long userId);
}
