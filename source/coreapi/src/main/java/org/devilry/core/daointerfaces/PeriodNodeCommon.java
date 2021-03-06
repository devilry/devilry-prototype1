package org.devilry.core.daointerfaces;

import java.util.Date;
import java.util.List;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;

public interface PeriodNodeCommon extends BaseNodeInterface {

	/**
	 * Create a new period-node.
	 * 
	 * @param name
	 *            A short name containing only lower-case English letters, '-',
	 *            '_' and numbers.
	 * @param displayName
	 *            A longer name typically used in GUI's instead of the name.
	 * @param start The date when the period starts.
	 * @param end The date when the period ends.
	 * @param parentId
	 *            The id of an existing course-node. The new period-node will
	 *            become a child of the given parent-node.
	 *            
	 * @return The id of the newly created period-node.
	 * @throws PathExistsException
	 *             If there already exists another period-node with the same
	 *             name and parentId.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not Admin on the parent node.
	 * @throws InvalidNameException
	 *             If the given name is not on the specified format.
	 * @throws NoSuchObjectException
	 *             If the given parent does not exist.
	 */
	long create(String name, String displayName, Date start, Date end,
			long parentId) throws PathExistsException, UnauthorizedException,
			InvalidNameException, NoSuchObjectException;

	/**
	 * The the parent-course-node of the given period-node.
	 * 
	 * @param periodNodeId
	 *            The id of an existing course-node.
	 * @return The id of the parent course-node.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	long getParentCourse(long periodNodeId) throws NoSuchObjectException, UnauthorizedException;

	/**
	 * Set the start-date of a period.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @param start
	 *            The start-date.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	void setStartDate(long periodNodeId, Date start)
			throws NoSuchObjectException, UnauthorizedException;

	/**
	 * Get the start-date of a period.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	Date getStartDate(long periodNodeId) throws NoSuchObjectException,
			UnauthorizedException;

	/**
	 * Set the end-date of a period.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @param end
	 *            The end-date.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */	
	void setEndDate(long periodNodeId, Date end) throws NoSuchObjectException,
			UnauthorizedException;

	/**
	 * Get the end-date of a period.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	Date getEndDate(long periodNodeId) throws NoSuchObjectException,
			UnauthorizedException;

	/**
	 * Get all assignment-nodes with the given period-node as parent.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @return A list with the id of the requested assignments.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	List<Long> getAssignments(long periodNodeId) throws NoSuchObjectException,
			UnauthorizedException;

	/**
	 * Get id of all students registered for the given period.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @return A list with the id of all Students for the given node.
	  * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	List<Long> getStudents(long periodNodeId) throws NoSuchObjectException,
			UnauthorizedException;

	/**
	 * Check if the authenticated user is Student on the given period-node.
	 * 
	 * @return <tt>true</tt> if the authenticated user is Student on the given
	 *         period-node.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	boolean isStudent(long periodNodeId) throws NoSuchObjectException, 
			UnauthorizedException;

	/**
	 * Add a new student to the given period-node.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @param userId
	 *            The id of an existing user.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * @throws NoSuchUserException
	 *             If the given user does not exist.
	 */
	void addStudent(long periodNodeId, long userId)
			throws NoSuchObjectException, UnauthorizedException,
			NoSuchUserException;

	/**
	 * Remove a Student from the given period-node.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @param userId
	 *            The id of an existing user.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not Admin on the
	 *             parent-course-node of the given period.
	 * @throws NoSuchUserException
	 *             If the given user does not exist.
	 */
	void removeStudent(long periodNodeId, long userId) 
			throws NoSuchObjectException, 
					UnauthorizedException, 
					NoSuchUserException;

	/**
	 * Get a list of period-nodes where the authenticated user is student.
	 * 
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized.
	 * @return A list of period-node-ids.
	 */
	List<Long> getPeriodsWhereIsStudent() throws UnauthorizedException;

	/**
	 * Get id of all Examiners registered for the given period.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @return A list with the id of all Examiners for the given node.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not Admin on the
	 *             parent-course-node of the given period.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 */
	List<Long> getExaminers(long periodNodeId) throws NoSuchObjectException,
			UnauthorizedException;

	/**
	 * Check if the authenticated user is Examiner on the given period-node.
	 * 
	 * @return <tt>true</tt> if the authenticated user is Examiner on the given
	 *         period-node.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not Admin on the
	 *             parent-course-node of the given period.
	 */
	boolean isExaminer(long periodNodeId) throws NoSuchObjectException, UnauthorizedException;

	/**
	 * Add a new Examiner to the given period-node.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @param userId
	 *            The id of an existing user.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not Admin on the
	 *             parent-course-node of the given period.
	 * @throws NoSuchUserException
	 *             If the given user does not exist.
	 */
	void addExaminer(long periodNodeId, long userId)
			throws NoSuchObjectException, UnauthorizedException, NoSuchUserException;

	/**
	 * Remove a Examiner from the given period-node.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @param userId
	 *            The id of an existing user.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not Admin on the
	 *             parent-course-node of the given period.
	 * @throws NoSuchUserException
	 *             If the given user does not exist.
	 */
	void removeExaminer(long periodNodeId, long userId) throws NoSuchObjectException, 
															   UnauthorizedException, 
															   NoSuchUserException;

	/**
	 * Get a list of nodes where the authenticated user is Examiner.
	 * 
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method. 
	 * @return List of period-node-ids.
	 * */
	List<Long> getPeriodsWhereIsExaminer() throws UnauthorizedException;

	/**
	 * Get a list of periods where the authenticated user is Admin.
	 * 
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * @return List of period-node-ids.
	 * */
	List<Long> getPeriodsWhereIsAdmin() throws UnauthorizedException;

	/**
	 * Check if the authenticated user is Admin on the given period-node,
	 * or on any of the nodes above the period in the tree.
	 * 
	 * @return <tt>true</tt> if the authenticated user is Admin on the given
	 *         period-node.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	boolean isPeriodAdmin(long periodNodeId) throws NoSuchObjectException,
		UnauthorizedException;

	/**
	 * Add a new Admin to the given period-node.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @param userId
	 *            The id of an existing user.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not Admin on the
	 *             parent-course-node of the given period.
	 * @throws NoSuchUserException
	 *             If the given user does not exist.
	 */
	void addPeriodAdmin(long periodNodeId, long userId)
			throws NoSuchObjectException, UnauthorizedException,
			NoSuchUserException;

	/**
	 * Remove a Admin from the given period-node.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @param userId
	 *            The id of an existing user.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not Admin on the
	 *             parent-course-node of the given period.
	 * @throws NoSuchUserException
	 *             If the given user does not exist.
	 */
	void removePeriodAdmin(long periodNodeId, long userId)
			throws NoSuchObjectException, UnauthorizedException,
			NoSuchUserException;;

	/**
	 * Get id of all Admins registered for the given period.
	 * 
	 * @param periodNodeId
	 *            The id of an existing period-node.
	 * @return A list with the id of all Admins for the given node.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not Admin on the
	 *             parent-course-node of the given period.
	 * @throws NoSuchObjectException
	 *             If no period-node with the given id exists.
	 */
	List<Long> getPeriodAdmins(long periodNodeId) throws UnauthorizedException,
			NoSuchObjectException;

}
