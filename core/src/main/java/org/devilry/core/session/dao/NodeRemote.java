package org.devilry.core.session.dao;

import javax.ejb.*;

@Remote
public interface NodeRemote {
	public boolean init(long nodeId);	
	public void setId(long nodeId);
	public long getId();
	public void setName(String name);
	public String getName();
	public void setDisplayName(String name);
	public String getDisplayName();
	public String getPath();
}

