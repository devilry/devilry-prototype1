package org.devilry.core.daointerfaces;

import java.util.List;

public interface CourseNodeLocal extends BaseNodeInterface {
	long create(String name, String displayName, long parentId);
	long getParent(long curseId);
	List<Long> getPeriods(long courseId);
	List<Long> getAllCourses();

	/** Get a list of courses where the authenticated user is admin.
	 * 
	 * @return List of course-ids.
	 * */
	List<Long> getCoursesWhereIsAdmin();
	
	
	/** Check if a course-node with the given id exists.
	 * @return True if a CourseNode with the given id exists.
	 * */
	boolean exists(long courseNodeId);
}