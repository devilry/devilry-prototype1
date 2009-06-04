package org.devilry.core.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.NodeRemote;
import org.devilry.core.entity.BaseNode;
import org.devilry.core.entity.Node;

@Stateless
@Interceptors( { AuthorizationInterceptor.class })
public class NodeImpl extends BaseNodeImpl implements NodeRemote, NodeLocal {
	@EJB
	private CourseNodeLocal courseBean;

	private Node getNode(long nodeId) {
		return getNode(Node.class, nodeId);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName) {
		if (getNodeIdFromPath(name) != -1) {
			throw new RuntimeException(
					"Node name must be unique on toplevel nodes.");
		}
		Node node = new Node();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		em.persist(node);
		em.flush();
		return node.getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName, long parentId) {
		Node node = new Node();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setParent(getNode(parentId));
		em.persist(node);
		em.flush();
		return node.getId();
	}

	public long getParent(long nodeId) {
		return getNode(nodeId).getParent().getId();
	}

	public List<Long> getToplevelNodes() {
		Query q = em
				.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NULL");
		return q.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(long nodeId) {
		removeNode(nodeId);
	}

	public List<Long> getChildnodes(long nodeId) {
		Query q = em
				.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NOT NULL AND n.parent.id = :parentId");
		q.setParameter("parentId", nodeId);
		return q.getResultList();
	}

	public List<Long> getChildcourses(long nodeId) {
		Query q = em
				.createQuery("SELECT c.id FROM CourseNode c WHERE c.parent.id = :parentId");
		q.setParameter("parentId", nodeId);
		return q.getResultList();
	}

	private void removeNode(Long nodeId) {

		// Remove childnodes
		List<Long> childNodes = getChildnodes(nodeId);
		for (Long childNodeId : childNodes) {
			removeNode(childNodeId);
		}

		// Remove child-courses
		List<Long> childCourses = getChildnodes(nodeId);
		for (Long courseId : childCourses) {
			courseBean.remove(courseId);
		}

		// Remove *this* node
		Query q = em.createQuery("DELETE FROM Node n WHERE n.id = :id");
		q.setParameter("id", nodeId);
		q.executeUpdate();
	}

	public String getPath(long nodeId) {
		Node cn = getNode(nodeId);
		String path = null;

		while (true) {
			if (cn.getParent() != null) {
				if (path == null)
					path = cn.getName();
				else
					path = cn.getName() + "." + path;

				cn = cn.getParent();
			} else {
				if (path == null)
					path = cn.getName();
				else
					path = cn.getName() + "." + path;
				break;
			}
		}

		return path;
	}

	public long getNodeIdFromPath(String path) {
		String[] sp = path.split("\\.");

		if (sp.length == 1) {
			return getNodeId(sp[0], -1);
		} else if (sp.length > 1) {
			int length = 0;
			long id = 0;

			while (length < sp.length - 1) {
				id = getNodeId(sp[length + 1], sp[length]);
				length++;

				if (id == -1)
					return id;
			}

			return id;
		}

		return -1;
	}

	private long getNodeId(String name, long parentId) {
		Query q;

		if (parentId != -1) {
			q = em
					.createQuery("SELECT n FROM Node n WHERE n.name=:name AND n.parent IS NOT NULL AND n.parent.id=:parentId");
			q.setParameter("name", name);
			q.setParameter("parentId", parentId);
		} else {
			q = em
					.createQuery("SELECT n FROM Node n WHERE n.name=:name AND n.parent IS NULL");
			q.setParameter("name", name);
		}

		Node node;

		try {
			node = (Node) q.getSingleResult();
		} catch (NoResultException e) {
			node = null;
		}

		return node == null ? -1 : node.getId();
	}

	private long getNodeId(String name, String parent) {
		Query q;

		if (parent != null) {
			q = em
					.createQuery("SELECT n FROM Node n WHERE n.name=:name AND n.parent IS NOT NULL AND n.parent.name=:parent");
			q.setParameter("name", name);
			q.setParameter("parent", parent);
		} else {
			q = em
					.createQuery("SELECT n FROM Node n WHERE n.name=:name AND n.parent IS NULL");
			q.setParameter("name", name);
		}

		Node node;

		try {
			node = (Node) q.getSingleResult();
		} catch (NoResultException e) {
			node = null;
		}

		return node == null ? -1 : node.getId();
	}

	public List<Long> getNodesWhereIsAdmin() {
		return getNodesWhereIsAdmin(Node.class);
	}

}