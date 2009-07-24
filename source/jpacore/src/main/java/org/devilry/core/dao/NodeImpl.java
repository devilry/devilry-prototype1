package org.devilry.core.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.authorize.AuthorizeNode;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.NodeRemote;
import org.devilry.core.entity.Node;

@Stateless
@Interceptors( { AuthorizeNode.class })
public class NodeImpl extends BaseNodeImpl implements NodeRemote, NodeLocal {

	@EJB(beanInterface = CourseNodeLocal.class)
	private CourseNodeCommon courseBean;

	private Node getNode(long nodeId) {
		return getNode(Node.class, nodeId);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public long create(String name, String displayName)
			throws PathExistsException, InvalidNameException {
		try {
			getIdFromPath(new NodePath(new String[] { name }));
			throw new PathExistsException(
					"Node name must be unique on toplevel nodes.");
		} catch (NoSuchObjectException e) {
		}

		Node node = new Node();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		em.persist(node);
		em.flush();
		return node.getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public long create(String name, String displayName, long parentId) {
		Node node = new Node();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setParent(getNode(parentId));
		em.persist(node);
		em.flush();
		return node.getId();
	}

	public long getParentNode(long nodeId) throws NoSuchObjectException {
		Node parent = getNode(nodeId).getParent();
		if (parent == null) {
			throw new NoSuchObjectException(String.format(
					"Node %d does not have a parent.", nodeId));
		} else {
			return parent.getId();
		}
	}

	public List<Long> getToplevelNodes() {
		Query q = em.createQuery("SELECT n.id FROM Node n "
				+ "WHERE n.parent IS NULL");
		return q.getResultList();
	}

	public List<Long> getChildNodes(long nodeId) {
		Query q = em.createQuery("SELECT n.id FROM Node n "
				+ "WHERE n.parent IS NOT NULL AND n.parent.id = :parentId");
		q.setParameter("parentId", nodeId);
		return q.getResultList();
	}

	public List<Long> getChildCourses(long nodeId) {
		Query q = em.createQuery("SELECT c.id FROM CourseNode c "
				+ "WHERE c.parent.id = :parentId");
		q.setParameter("parentId", nodeId);
		return q.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(long nodeId) throws NoSuchObjectException,
			UnauthorizedException {
		// Remove childnodes
		List<Long> childNodes = getChildNodes(nodeId);
		for (Long childNodeId : childNodes) {
			remove(childNodeId);
		}

		// Remove child-courses
		List<Long> childCourses = getChildCourses(nodeId);
		for (Long courseId : childCourses) {
			courseBean.remove(courseId);
		}

		// Remove *this* node
		removeNode(nodeId, Node.class);
	}

	public NodePath getPath(long nodeId) throws NoSuchObjectException,
			InvalidNameException {

		Node node = getNode(nodeId);

		// No node with given NodeId
		if (node == null)
			throw new NoSuchObjectException("No node with id " + nodeId);

		String nodeName = node.getName();

		NodePath path;

		// If node has parent node
		if (node.getParent() != null) {
			// Get path from parent node
			path = getPath(node.getParent().getId());
			// Add current node name to path
			path.addToEnd(nodeName);
		} else {
			path = new NodePath(new String[] { nodeName });
		}

		return path;
	}

	public long getIdFromPath(NodePath nodePath) throws NoSuchObjectException {

		NodePath pathCopy = new NodePath(nodePath);
		if (nodePath.size() == 0) {
			throw new NoSuchObjectException("No node with path: " + pathCopy);
		}

		String toplevelName = pathCopy.removeFirstPathElement();
		try {
			long nodeId = getToplevelNodeByName(toplevelName);
			for (String name : pathCopy) {
				nodeId = getNodeId(name, nodeId);
			}
			return nodeId;
		} catch (NoResultException e) {
			throw new NoSuchObjectException("No node with path: " + pathCopy);
		}
	}

	private long getToplevelNodeByName(String name) {
		Query q = em
				.createQuery("SELECT n.id FROM Node n WHERE n.name =: name "
						+ "AND n.parent IS NULL");
		q.setParameter("name", name);
		return (Long) q.getSingleResult();
	}

	private long getNodeId(String name, long parentId) {
		Query q;
		q = em.createQuery("SELECT n.id FROM Node n WHERE n.name = :name "
				+ "AND n.parent IS NOT NULL AND n.parent.id = :parentId");
		q.setParameter("name", name);
		q.setParameter("parentId", parentId);
		return (Long) q.getSingleResult();
	}

	public List<Long> getNodesWhereIsAdmin() {
		return getNodesWhereIsAdmin(Node.class);
	}

	public boolean isNodeAdmin(long nodeId) {
		Node node = getNode(nodeId);
		if (isAdmin(node, userBean.getAuthenticatedUser())) {
			return true;
		}

		try {
			long parentId = getParentNode(nodeId);
			return isNodeAdmin(parentId);
		} catch (NoSuchObjectException ex) {
			return false;
		}
	}

	public void addNodeAdmin(long nodeId, long userId) {
		Node node = getNode(nodeId);
		addAdmin(node, userId);
	}

	public void removeNodeAdmin(long nodeId, long userId) {
		Node node = getNode(nodeId);
		removeAdmin(node, userId);
	}

	public List<Long> getNodeAdmins(long nodeId) {
		Node node = getNode(nodeId);
		return getAdmins(node);
	}
}
