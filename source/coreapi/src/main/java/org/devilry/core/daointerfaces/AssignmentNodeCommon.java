package org.devilry.core.daointerfaces;

import java.util.Date;
import java.util.List;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;

/** Interface to assignment manipulation. */
public interface AssignmentNodeCommon extends BaseNodeInterface {

	/**
	 * Create a new assignment-node.
	 * 
	 * @param name
	 *            A short name containing only lower-case English letters, '-',
	 *            '_' and numbers.
	 * @param displayName
	 *            A longer name typically used in GUI's instead of the name.
	 * @param deadline
	 *            The date/time of the deadline.
	 * @param parentId
	 *            The id of an existing period-node. The new node will become a
	 *            child of the given parent-node.
	 * @return The id of the newly created assignment-node.
	 * @throws PathExistsException
	 *             If there already exists another course-node with the same
	 *             name and parentId.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * @throws InvalidNameException
	 *             If the given name is not on the specified format.
	 * @throws NoSuchObjectException
	 *             If the given parent does not exist.
	 */
	long create(String name, String displayName, Date deadline, long parentId)
			throws PathExistsException, UnauthorizedException,
			InvalidNameException, NoSuchObjectException;

	/**
	 * The the parent-period-node of the given assignment-node.
	 * 
	 * @param assignmentNodeId
	 *            The id of an existing assignment-node.
	 * @return The id of the parent period-node.
	 * @throws NoSuchObjectException
	 *             If no assignment-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 */
	long getParentPeriod(long assignmentNodeId) throws NoSuchObjectException, UnauthorizedException;

	/**
	 * Get the deadline for the given assignment.
	 * 
	 * @param assignmentNodeId
	 *            The id of an existing assignment-node.
	 * @return the date/time of the deadline.
	 * @throws NoSuchObjectException
	 *             If no assignment-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 */
	Date getDeadline(long assignmentNodeId) throws UnauthorizedException;

	/**
	 * Set the deadline for this assignment.
	 * 
	 * @param assignmentNodeId
	 *            The id of an existing assignment-node.
	 * @param deadline
	 *            The date/time of the deadline.
	 * @throws NoSuchObjectException
	 *             If no assignment-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 */
	void setDeadline(long assignmentNodeId, Date deadline)
			throws UnauthorizedException, NoSuchObjectException;

	/**
	 * Get all deliveries with this assignment as parent.
	 * 
	 * @param assignmentNodeId
	 *            The id of an existing assignment-node.
	 * @return List of IDs for the deliveries.
	 * @throws NoSuchObjectException
	 *             If no assignment-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not <em>Admin</em> on the given
	 *             assignment.
	 */
	List<Long> getDeliveries(long assignmentNodeId)
			throws NoSuchObjectException, UnauthorizedException;

	
	/**
	 * Get a list of assignments where the authenticated user is Admin.
	 * 
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 * @return List of assignment-ids.
	 * */
	List<Long> getAssignmentsWhereIsAdmin() throws UnauthorizedException;

	/**
	 * Add a new administrator to the given assignment node.
	 * 
	 * @param assignmentNodeId
	 *            The id of an existing assignment-node.
	 * @param userId
	 *            The unique number identifying an existing user.
	 * @throws NoSuchObjectException
	 *             If no assignment-node with the given id exists.
	 * @throws NoSuchUserException
	 *             If the given user does not exist.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 */
	void addAssignmentAdmin(long assignmentNodeId, long userId)
			throws NoSuchObjectException, NoSuchUserException,
			UnauthorizedException;

	/**
	 * Remove an administrator from the given assignment node.
	 * 
	 * @param assignmentNodeId
	 *            The id of an existing assignment-node.
	 * @param userId
	 *            The unique number identifying an existing user.
	 * @throws NoSuchObjectException
	 *             If no course-node with the given id exists.
	 * @throws NoSuchUserException
	 *             If the given user does not exist.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 */
	void removeAssignmentAdmin(long assignmentNodeId, long userId)
			throws NoSuchObjectException, NoSuchUserException,
			UnauthorizedException;

	/**
	 * Get the id of all administrators registered for the given assignment
	 * node.
	 * 
	 * @param assignmentNodeId
	 *            The id of an existing assignment-node.
	 * @return A list with the id of all administrators for the given node.
	 * @throws NoSuchObjectException
	 *             If no course-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 */
	List<Long> getAssignmentAdmins(long assignmentNodeId)
			throws NoSuchObjectException, UnauthorizedException;

	/**
	 * Check if the authenticated user is Admin on the given assignment-node, or
	 * on any of the nodes above the assignment in the tree.
	 * 
	 * @param assignmentNodeId
	 *            The id of an existing assignment-node.
	 * @throws NoSuchObjectException
	 *             If no assignment-node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method
	 */
	boolean isAssignmentAdmin(long assignmentNodeId)
			throws NoSuchObjectException, UnauthorizedException ;

}
