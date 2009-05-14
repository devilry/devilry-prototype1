package org.devilry.core.session;

import javax.ejb.*;
import java.util.Date;

@Remote
public interface TreeManagerRemote {
	public long addNode(String name, String displayName);
	public long addNode(String name, String displayName, long parentId);

	public long addCourseNode(String name, String displayName, long parentId);
	public long addCourseNode(String name, String courseCode, String displayName, long parentId);

	public long addPeriodNode(String name, Date start, Date end, long parentId);
	public long addPeriodNode(String name, String displayName, Date start, Date end, long parentId);

	public long getNodeIdFromPath(String path);
}

