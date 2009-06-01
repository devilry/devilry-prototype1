package org.devilry.core.session.dao;

import java.util.List;

public interface CourseNodeLocal extends BaseNodeInterface {
	public List<Long> getAllCourses();
	long create(String name, String displayName, long parentId);
}
