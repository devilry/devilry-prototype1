package org.devilry.core.daointerfaces;

import java.util.List;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoParentException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;

/** Common interface for local and remote operations on Nodes. */
public interface NodeCommon extends BaseNodeInterface {

	/**
	 * Create a new toplevel node.
	 * 
	 * @param name
	 *            A short name containing only lower-case English letters, '-',
	 *            '_' and numbers.
	 * @param displayName
	 *            A longer name typically used in GUI's instead of the name.
	 * @return The id of the newly created node.
	 * @throws PathExistsException
	 *             If there already exists another toplevel node with the given
	 *             name.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * @throws InvalidNameException
	 *             If the given name is not on the specified format.
	 */
	long create(String name, String displayName) throws PathExistsException,
			UnauthorizedException, InvalidNameException;

	/**
	 * Create a new node.
	 * 
	 * @param name
	 *            A short name containing only lower-case English letters, '-',
	 *            '_' and numbers.
	 * @param displayName
	 *            A longer name typically used in GUI's instead of the name.
	 * @param parentId
	 *            The id of an existing node. The new node will become a child
	 *            of the given parent-node.
	 * @return The id of the newly created node.
	 * @throws PathExistsException
	 *             If there already exists another node with the same name and
	 *             parentId.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
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
	 * @param nodeId
	 *            The id of an existing node.
	 * @return The id of the parent node.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * @throws NoParentException
	 *             If the given node is a toplevel node.
	 */
	long getParentNode(long nodeId) throws NoSuchObjectException,
			NoParentException, UnauthorizedException;

	/**
	 * Get all nodes with <em>this</em> node as parent.
	 * 
	 * @param nodeId
	 *            The id of an existing node.
	 * @return A list with the id's of all childnodes.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	List<Long> getChildNodes(long nodeId) throws UnauthorizedException,
			NoSuchObjectException;

	/**
	 * Get all nodes with <em>this</em> node as parent.
	 * 
	 * @param nodeId
	 *            A unique identificator of an existing node.
	 * @return A list with the id's of all childnodes.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	List<Long> getChildCourses(long nodeId) throws UnauthorizedException,
			NoSuchObjectException;

	/**
	 * Get all nodes without a parent-node.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * @return A list with the id's of all toplevel nodes.
	 */
	List<Long> getToplevelNodes() throws UnauthorizedException;

	/**
	 * Get a list of nodes where the authenticated user is admin.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * @return List of user-ids.
	 */
	List<Long> getNodesWhereIsAdmin() throws UnauthorizedException;

	/**
	 * Check if the authenticated user is admin on the given node.
	 * 
	 * @param nodeId
	 *            The id of a existing node.
	 * @return <tt>true</tt> if the authenticated user is admin on the given
	 *         node.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 * */
	boolean isNodeAdmin(long nodeId) throws NoSuchObjectException,
		UnauthorizedException;

	/**
	 * Check if the authenticated user is Admin on the given node, or on any of
	 * the nodes above the node in the tree.
	 * 
	 * @param nodeId
	 *            The id of a existing node.
	 * @param userId
	 *            The id of a existing user.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 * @throws NoSuchUserException
	 *             If the the given user does not exist.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	void addNodeAdmin(long nodeId, long userId) throws NoSuchObjectException,
			NoSuchUserException, UnauthorizedException;

	/**
	 * Remove an administrator from the given node.
	 * 
	 * @param nodeId
	 *            The id of a existing node.
	 * @param userId
	 *            The id of a existing user.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 * @throws NoSuchUserException
	 *             If the the given user does not exist.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	void removeNodeAdmin(long nodeId, long userId)
			throws NoSuchObjectException, NoSuchUserException,
			UnauthorizedException;

	/**
	 * Get id of all administrators registered for the given node.
	 * 
	 * @param nodeId
	 *            The id of a existing node.
	 * @return A list with the id of all administrators for the given node.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not authorized for this method.
	 */
	List<Long> getNodeAdmins(long nodeId) throws NoSuchObjectException,
		UnauthorizedException;
}
