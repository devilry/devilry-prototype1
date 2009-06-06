package org.devilry.core.daointerfaces;

import java.util.List;

import org.devilry.core.NodePath;


public interface BaseNodeInterface {
	void setName(long baseNodeId, String name);
	String getName(long baseNodeId);
	void setDisplayName(long baseNodeId, String name);
	String getDisplayName(long baseNodeId);

	void remove(long baseNodeId);
	boolean exists(long baseNodeId);

	/** 
	 * Get path from id.
	 */
	NodePath getPath(long baseNodeId);
	
	/** Get id from path. */
	public long getIdFromPath(NodePath nodePath, long parentNodeId);	
}
