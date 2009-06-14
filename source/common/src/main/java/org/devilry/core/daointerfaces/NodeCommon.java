package org.devilry.core.daointerfaces;

import java.util.List;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;

public interface NodeCommon extends BaseNodeInterface {

	// TODO: Duplicate exception!
	public long create(String name, String displayName);

	// TODO: Duplicate exception!
	public long create(String name, String displayName, long parentId);

	public long getParentNode(long nodeId) throws NoSuchObjectException;

	/** Get all nodes with <em>this</em> node as parent.
	 *
	 * @param nodeId A unique identificator of an existing node.
	 * @return A list with the id's of all childnodes.
	 * @throws UnauthorizedException If the authorized user is not
	 *		<em>NodeAdmin</em> on the given node.
	 */
	public List<Long> getChildnodes(long nodeId) throws UnauthorizedException;

	/** Get all nodes with <em>this</em> node as parent.
	 *
	 * @param nodeId A unique identificator of an existing node.
	 * @return A list with the id's of all childnodes.
	 * @throws UnauthorizedException If the authorized user is not
	 *		<em>NodeAdmin</em> on the given node.
	 */
	public List<Long> getChildcourses(long nodeId) throws UnauthorizedException;

	/** Get all nodes without a parent-node.
	 * 
	 * @return A list with the id's of all toplevel nodes.
	 */
	public List<Long> getToplevelNodes();

	/** Get a list of nodes where the authenticated user is admin.
	 * 
	 * @return List of user-ids.
	 */
	public List<Long> getNodesWhereIsAdmin();

	/** 
	 * Check if the authenticated user is admin on the given node. 
	 * */
	boolean isNodeAdmin(long nodeId);

	/** 
	 * Add a new administrator to the given node.
	 * @param nodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	public void addNodeAdmin(long nodeId, long userId);

	/** 
	 * Remove an administrator from the given node.
	 * @param nodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	public void removeNodeAdmin(long nodeId, long userId);

	/** 
	 * Get id of all administrators registered for the given node.
	 * 
	 * @param baseNodeId The unique number identifying an existing node.
	 * @return A list with the id of all administrators for the given node.
	 */
	public List<Long> getNodeAdmins(long nodeId);
}
