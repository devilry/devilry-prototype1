package org.devilry.core.daointerfaces;

import java.util.List;

public interface NodeCommon extends BaseNodeInterface {
	long create(String name, String displayName);
	long create(String name, String displayName, long parentId);

	long getParentNode(long nodeId);

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
	 * Check if a user is admin on the given node. 
	 * */
	boolean isNodeAdmin(long nodeId, long userId);
	
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

	
	/** 
	 * Get id of all administrators registered for the given node.
	 * 
	 * @param baseNodeId The unique number identifying an existing node.
	 * @return A list with the id of all administrators for the given node.
	 */
	List<Long> getNodeAdmins(long nodeId);
	
}
