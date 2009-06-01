package org.devilry.core.daointerfaces;

import java.util.List;


public interface BaseNodeInterface {
	void setName(long nodeId, String name);
	String getName(long nodeId);
	void setDisplayName(long nodeId, String name);
	String getDisplayName(long nodeId);
	String getPath(long nodeId);

	List<Long> getChildren(long nodeId);
	List<Long> getSiblings(long nodeId);

	void remove(long nodeId);
	long getParent(long nodeId);
	boolean exists(long nodeId);

	List<Long> getToplevelNodes();
	public long getNodeIdFromPath(String path);
}
