package org.devilry.core.daointerfaces;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

public interface CourseNodeCommon extends BaseNodeInterface {
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
	 */
	boolean exists(long courseNodeId);
	
	
	/** 
	 * Check if a user is admin on the given course node. 
	 * */
	boolean isCourseAdmin(long courseNodeId, long userId);
	
	/** 
	 * Add a new administrator to the given course node.
	 * @param courseNodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	public void addCourseAdmin(long courseNodeId, long userId);
	
	
	/** 
	 * Remove an administrator from the given course node.
	 * @param courseNodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	public void removeCourseAdmin(long courseNodeId, long userId);
	
	
	/** 
	 * Get id of all administrators registered for the given course node.
	 * 
	 * @param baseNodeId The unique number identifying an existing node.
	 * @return A list with the id of all administrators for the given node.
	 */
	List<Long> getCourseAdmins(long courseNodeId);
	
	public long getNodeIdFromPath(String [] nodePath, long parentNodeId);
	
}