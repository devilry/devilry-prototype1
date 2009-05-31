package org.devilry.core.session.dao;

import javax.ejb.*;
import java.util.List;

@Remote
public interface NodeRemote {
	void setName(long nodeId, String name);
	String getName(long nodeId);
	void setDisplayName(long nodeId, String name);
	String getDisplayName(long nodeId);
	String getPath(long nodeId);

	List<Long> getChildren(long nodeId);
	List<Long> getSiblings(long nodeId);

	void remove(long nodeId);
	long getParentId(long nodeId);
	boolean exists(long nodeId);

	long create(String name, String displayName);
	long create(String name, String displayName, long parentId);
	List<Long> getToplevelNodes();
	public long getNodeIdFromPath(String path);	
}