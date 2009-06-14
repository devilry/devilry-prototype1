package org.devilry.core.daointerfaces;

import java.util.List;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoParentException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NoSuchUserException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;

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
	 *             If the authenticated user is not SuperAdmin.
	 * @throws InvalidNameException
	 *             If the given name is not on the specified format.
	 */
	public long create(String name, String displayName)
			throws PathExistsException, UnauthorizedException,
			InvalidNameException;

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
	 *             If the authenticated user is not Admin on the parent node.
	 * @throws InvalidNameException
	 *             If the given name is not on the specified format.
	 * @throws NoSuchObjectException
	 *             If the given parent does not exist.
	 */
	public long create(String name, String displayName, long parentId)
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
	 * @throws NoParentException
	 *             If the given node is a toplevel node.
	 */
	public long getParentNode(long nodeId) throws NoSuchObjectException,
			NoParentException;

	/**
	 * Get all nodes with <em>this</em> node as parent.
	 * 
	 * @param nodeId
	 *            The id of an existing node.
	 * @return A list with the id's of all childnodes.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not <em>Admin</em> on the given
	 *             node.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	public List<Long> getChildnodes(long nodeId) throws UnauthorizedException,
			NoSuchObjectException;

	/**
	 * Get all nodes with <em>this</em> node as parent.
	 * 
	 * @param nodeId
	 *            A unique identificator of an existing node.
	 * @return A list with the id's of all childnodes.
	 * @throws UnauthorizedException
	 *             If the authenticated user is not <em>NodeAdmin</em> on the
	 *             given node.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	public List<Long> getChildcourses(long nodeId)
			throws UnauthorizedException, NoSuchObjectException;

	/**
	 * Get all nodes without a parent-node.
	 * 
	 * @return A list with the id's of all toplevel nodes.
	 */
	public List<Long> getToplevelNodes();

	/**
	 * Get a list of nodes where the authenticated user is admin.
	 * 
	 * @return List of user-ids.
	 */
	public List<Long> getNodesWhereIsAdmin();

	/**
	 * Check if the authenticated user is admin on the given node.
	 * 
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 * */
	boolean isNodeAdmin(long nodeId) throws NoSuchObjectException;

	/**
	 * Check if the authenticated user is Admin on the given node,
	 * or on any of the nodes above the node in the tree.
	 * 
	 * @param nodeId
	 *            The unique number identifying an existing node.
	 * @param userId
	 *            The unique number identifying an existing user.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 * @throws NoSuchUserException
	 *             If the the given user does not exist.
	 */
	public void addNodeAdmin(long nodeId, long userId)
			throws NoSuchObjectException, NoSuchUserException;

	/**
	 * Remove an administrator from the given node.
	 * 
	 * @param nodeId
	 *            The unique number identifying an existing node.
	 * @param userId
	 *            The unique number identifying an existing user.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 * @throws NoSuchUserException
	 *             If the the given user does not exist.
	 */
	public void removeNodeAdmin(long nodeId, long userId)
			throws NoSuchObjectException, NoSuchUserException;

	/**
	 * Get id of all administrators registered for the given node.
	 * 
	 * @param baseNodeId
	 *            The unique number identifying an existing node.
	 * @return A list with the id of all administrators for the given node.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	public List<Long> getNodeAdmins(long nodeId) throws NoSuchObjectException;
}
