package org.devilry.core.daointerfaces;

import java.util.List;

public interface NodeLocal extends BaseNodeInterface {
	long create(String name, String displayName);
	long create(String name, String displayName, long parentId);

	long getParent(long nodeId);

	/** Get a list of all nodes without a parent-node.
	 * 
	 * @return List of node-ids.
	 * */
	List<Long> getToplevelNodes();

	/** Get a list of nodes where the authenticated user is admin.
	 * 
	 * @return List of user-ids.
	 * */
	List<Long> getNodesWhereIsAdmin();
}
