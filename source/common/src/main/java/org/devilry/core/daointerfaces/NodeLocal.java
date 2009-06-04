package org.devilry.core.daointerfaces;

import java.util.List;

public interface NodeLocal extends BaseNodeInterface {
	long create(String name, String displayName);
	long create(String name, String displayName, long parentId);

	/** Get a list of all nodes without a parent-node.
	 * 
	 * @return List of node-ids.
	 * */
	List<Long> getToplevelNodes();
}
