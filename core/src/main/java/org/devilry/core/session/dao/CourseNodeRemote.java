package org.devilry.core.session.dao;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface CourseNodeRemote extends BaseNodeInterface {
	public List<Long> getAllCourses();
	long create(String name, String displayName, long parentId);
}
