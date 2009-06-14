package org.devilry.core.daointerfaces;

import java.util.List;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;

/** Interface for course related methods. */
public interface CourseNodeCommon extends BaseNodeInterface {

	/**
	 * Create a new course-node.
	 * 
	 * @param name
	 *            A short name containing only lower-case English letters, '-',
	 *            '_' and numbers.
	 * @param displayName
	 *            A longer name typically used in GUI's instead of the name.
	 * @param parentId
	 *            The id of an existing node. The new node will become a child
	 *            of the given parent-node.
	 * @return The id of the newly created course-node.
	 * @throws PathExistsException
	 *             If there already exists another course-node with the same
	 *             name and parentId.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not Admin on the parent node.
	 * @throws InvalidNameException
	 *             If the given name is not on the specified format.
	 * @throws NoSuchObjectException
	 *             If the given parent does not exist.
	 */
	long create(String name, String displayName, long parentId)
			throws PathExistsException, UnauthorizedException,
			InvalidNameException, NoSuchObjectException;

	/**
	 * The the parent-node of the given node.
	 * 
	 * @param courseNodeId
	 *            The id of an existing course-node.
	 * @return The id of the parent node.
	 * @throws NoSuchObjectException
	 *             If no course-node with the given id exists.
	 */
	long getParentNode(long curseNodeId);

	/**
	 * Get all period-nodes with the given course-node as parent.
	 * 
	 * @param courseNodeId
	 *            The id of an existing course-node.
	 * @return A list with the id of the requested periods.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not <em>Admin</em> on the given
	 *             course.
	 * @throws NoSuchObjectException
	 *             If no course-node with the given id exists.
	 */
	List<Long> getPeriods(long courseNodeId) throws NoSuchObjectException,
			UnauthorizedException;

	/**
	 * Get a list of courses where the authenticated user is Admin.
	 * 
	 * @return List of course-ids.
	 * */
	List<Long> getCoursesWhereIsAdmin();

	/**
	 * Check if the authenticated user is Admin on the given course-node.
	 * 
	 * @throws NoSuchObjectException
	 *             If no course-node with the given id exists.
	 * */
	boolean isCourseAdmin(long courseNodeId) throws NoSuchObjectException;

	/**
	 * Add a new administrator to the given course node.
	 * 
	 * @param courseNodeId
	 *            The unique number identifying an existing node.
	 * @param userId
	 *            The unique number identifying an existing user.
	 * @throws NoSuchObjectException
	 *             If no course-node with the given id exists.
	 * @throws NoSuchUserException
	 *             If the given user does not exist.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not <em>Admin</em> on the given
	 *             course.
	 */
	void addCourseAdmin(long courseNodeId, long userId)
			throws NoSuchObjectException, NoSuchUserException,
			UnauthorizedException;

	/**
	 * Remove an administrator from the given course node.
	 * 
	 * @param courseNodeId
	 *            The unique number identifying an existing node.
	 * @param userId
	 *            The unique number identifying an existing user.
	 * @throws NoSuchObjectException
	 *             If no course-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not <em>Admin</em> on the given
	 *             course.
	 */
	void removeCourseAdmin(long courseNodeId, long userId)
			throws NoSuchObjectException, NoSuchUserException,
			UnauthorizedException;

	/**
	 * Get id of all administrators registered for the given course node.
	 * 
	 * @param baseNodeId
	 *            The unique number identifying an existing node.
	 * @return A list with the id of all administrators for the given node.
	 * @throws NoSuchObjectException
	 *             If no course-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not <em>Admin</em> on the given
	 *             course.
	 */
	List<Long> getCourseAdmins(long courseNodeId) throws NoSuchObjectException,
			UnauthorizedException;

}