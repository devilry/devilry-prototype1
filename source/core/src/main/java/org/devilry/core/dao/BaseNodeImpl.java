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

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(long nodeId) {
		removeNode(nodeId);
	}

	public List<Long> getChildren(long nodeId) {
		Query q = em
				.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NOT NULL AND n.parent.id = :parentId");
		q.setParameter("parentId", nodeId);
		return (List<Long>) q.getResultList();
	}

	private void removeNode(Long nodeId) {

		// TODO: find out why this is not required!
		// // Handle assignment nodes different because they do not have nodes
		// below them.
		// Node node = getNode(nodeId);
		// if(node instanceof AssignmentNode) {
		// System.out.println("********* REMOVING ASSIGNMENT " + node + " #### "
		// + assignmentBean); //+ node.getName());
		// long id = node.getId();
		// assignmentBean.remove(id);
		// return;
		// }

		// Remove childnodes first
		Query q = em
				.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NOT NULL AND n.parent.id=:parentId");
		q.setParameter("parentId", nodeId);
		List<Long> children = q.getResultList();
		for (Long childNodeId : children) {
			removeNode(childNodeId);
		}

		// Remove *this* node
		q = em.createQuery("DELETE FROM Node n WHERE n.id = :id");
		q.setParameter("id", nodeId);
		q.executeUpdate();
	}

	// /////////////////////////////////
	// Admins operations
	// /////////////////////////////////

	public List<Long> getAdmins(long nodeId) {
		LinkedList<Long> l = new LinkedList<Long>();
		for (User u : getBaseNode(nodeId).getAdmins())
			l.add(u.getId());
		return l;
	}

	/** Get a User by id. */
	protected User getUser(long userId) {
		return (User) em.find(User.class, userId);
	}

	public boolean isAdmin(long nodeId, long userId) {
		return getBaseNode(nodeId).getAdmins().contains(getUser(userId));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addAdmin(long nodeId, long userId) {
		BaseNode n = getBaseNode(nodeId);
		n.getAdmins().add(getUser(userId));
		em.merge(n);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeAdmin(long nodeId, long userId) {
		BaseNode n = getBaseNode(nodeId);
		n.getAdmins().remove(getUser(userId));
		em.merge(n);
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
		Query q = em.createQuery(String.format(query, nodeEntityClass
				.getSimpleName()));
		q.setParameter("userId", userId);
		return q.getResultList();
	}
}
