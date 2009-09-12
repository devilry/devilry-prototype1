package org.devilry.core.daointerfaces;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;

public interface BaseNodeInterface {

	/**
	 * Set the name of the given node.
	 * 
	 * @param baseNodeId
	 *            The unique id of an existing node.
	 * @param name
	 *            The name of a node must be unique among its sibling-nodes.
	 * @throws UnauthorizedException
	 *             If the authorized user is not Admin on the parent-node of the
	 *             given node. Toplevel nodes can only be modified by a
	 *             SuperAdmin.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	void setName(long baseNodeId, String name) throws UnauthorizedException,
			NoSuchObjectException;

	/**
	 * Get the name of a node.
	 * 
	 * @param baseNodeId
	 *            The unique id of an existing node.
	 * @return The name of the given node.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	String getName(long baseNodeId) throws NoSuchObjectException, 
											UnauthorizedException;

	/**
	 * Set the display-name of the given node.
	 * 
	 * @param baseNodeId
	 *            The unique id of an existing node.
	 * @param displayName
	 *            A string which is typically used instead of the <em>name</em>
	 *            in user-interfaces.
	 * @throws UnauthorizedException
	 *             If the authorized user is not Admin on the parent-node of the
	 *             given node. Toplevel nodes can only be modified by a
	 *             SuperAdmin.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	void setDisplayName(long baseNodeId, String displayName)
			throws UnauthorizedException, NoSuchObjectException;

	/**
	 * Get the display-name of the given node.
	 * 
	 * @param baseNodeId
	 *            The unique id of an existing node.
	 * @return The display-name of the given node.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	String getDisplayName(long baseNodeId) throws NoSuchObjectException, 
												  UnauthorizedException
	;

	/**
	 * Remove the given node.
	 * 
	 * @param baseNodeId
	 *            The unique id of an existing node.
	 * @throws UnauthorizedException
	 *             If the authorized user is not Admin on the parent-node of the
	 *             given node. Toplevel nodes can only be removed by a
	 *             SuperAdmin.
	 * @throws NotEmptyException
	 *             If the given node has any child-nodes. This exception is
	 *             never thrown if the authorized user is SuperAdmin because
	 *             SuperAdmin can remove non-empty nodes.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	void remove(long baseNodeId) throws NoSuchObjectException,
			UnauthorizedException;

	/**
	 * Check if the given node exists.
	 * 
	 * @param baseNodeId
	 *            The unique id of an existing node.
	 * @return true if a node with the given id exists.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	boolean exists(long baseNodeId) 
			throws NoSuchObjectException, UnauthorizedException;

	/**
	 * Get the path of a node.
	 * 
	 * @param baseNodeId
	 *            The unique id of an existing node.
	 * @return The path of the given node.
	 * @throws NoSuchObjectException
	 *             If no node with the given id exists.
	 */
	NodePath getPath(long baseNodeId) throws NoSuchObjectException,
			InvalidNameException, UnauthorizedException;

	/**
	 * Get id from path.
	 * 
	 * @param nodePath
	 *            The path of a node.
	 * @return The id of the node at the given path.
	 * @throws NoSuchObjectException
	 *             If the node with the given path does not exist.
	 */
	long getIdFromPath(NodePath nodePath) 
			throws NoSuchObjectException, UnauthorizedException;
}
