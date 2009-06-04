package org.devilry.core.daointerfaces;

import java.util.List;

public interface CourseNodeLocal extends BaseNodeInterface {
	long create(String name, String displayName, long parentId);
	long getParent(long nodeId);
	List<Long> getPeriods(long courseId);
	List<Long> getAllCourses();

	/** Get a list of courses where the authenticated user is admin.
	 * 
	 * @return List of course-ids.
	 * */
	List<Long> getCoursesWhereIsAdmin();
}
