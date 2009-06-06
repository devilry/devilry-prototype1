package org.devilry.core.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.devilry.core.daointerfaces.BaseNodeInterface;
import org.devilry.core.daointerfaces.UserLocal;
import org.devilry.core.entity.BaseNode;
import org.devilry.core.entity.Node;
import org.devilry.core.entity.User;

public abstract class BaseNodeImpl implements BaseNodeInterface {
	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;

	@EJB
	protected UserLocal userBean;

	private BaseNode getBaseNode(long nodeId) {
		return getNode(BaseNode.class, nodeId);
	}

	/**
	 * Load the entity for the given node-id.
	 * 
	 * @param <E>
	 *            Node entity type.
	 * @param nodeEntityClass
	 *            The return-type.
	 * @param nodeId
	 *            A unique id for the given
	 * @return The node with the given id.
	 */
	protected <E> E getNode(Class<E> nodeEntityClass, long nodeId) {
		return em.find(nodeEntityClass, nodeId);
	}

	public boolean exists(long nodeId) {
		return getBaseNode(nodeId) != null;
	}

	public String getName(long nodeId) {
		return getBaseNode(nodeId).getName();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setName(long nodeId, String name) {
		BaseNode node = getBaseNode(nodeId);
		node.setName(name);
		em.merge(node);
	}

	public String getDisplayName(long nodeId) {
		return getBaseNode(nodeId).getDisplayName();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setDisplayName(long nodeId, String name) {
		BaseNode node = getBaseNode(nodeId);
		node.setDisplayName(name);
		em.merge(node);
	}

	// /////////////////////////////////
	// Admins operations
	// /////////////////////////////////

	public List<Long> getAdmins(BaseNode node) {
		LinkedList<Long> l = new LinkedList<Long>();
		for (User u : node.getAdmins())
			l.add(u.getId());
		return l;
	}

	/** Get a User by id. */
	protected User getUser(long userId) {
		return (User) em.find(User.class, userId);
	}

	public boolean isAdmin(BaseNode node, long userId) {
		return node.getAdmins().contains(getUser(userId));
	}

	/** Add a new administrator to the given node.
	 * 
	 * @param baseNodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	protected void addAdmin(BaseNode node, long userId) {
		node.getAdmins().add(getUser(userId));
		em.merge(node);
	}

	/** Remove an administrator from the given node.
	 * 
	 * @param baseNodeId The unique number identifying an existing node.
	 * @param userId The unique number identifying an existing user.
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	protected void removeAdmin(BaseNode node, long userId) {
		node.getAdmins().remove(getUser(userId));
		em.merge(node);
	}


	/**
	 * Remove a node with id nodeId of type nodeEntityClass
	 * @param nodeId
	 * @param nodeEntityClass
	 */
	protected void removeNode(long nodeId, Class<?> nodeEntityClass) {
		em.remove(em.find(nodeEntityClass, nodeId));
	}
	
	/**
	 * Get nodes of the given type where the authenticated user is admin.
	 * 
	 * @param nodeEntityClass
	 *            The node type to search.
	 * @return List of node-ids.
	 */
	protected List<Long> getNodesWhereIsAdmin(Class<?> nodeEntityClass) {
		long userId = userBean.getAuthenticatedUser();
		String query = "SELECT n.id FROM %s n INNER JOIN n.admins user WHERE user.id = :userId";
		Query q = em.createQuery(String.format(query, nodeEntityClass.getName()));
		q.setParameter("userId", userId);
		return q.getResultList();
	}
}
