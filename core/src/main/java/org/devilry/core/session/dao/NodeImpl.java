package org.devilry.core.session.dao;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

import org.devilry.core.entity.*;

@Stateless
public class NodeImpl implements NodeRemote {
	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;

	protected Node getNode(long nodeId) {
		return em.find(Node.class, nodeId);
	}

	public String getName(long nodeId) {
		return getNode(nodeId).getName();
	}

	public String getDisplayName(long nodeId) {
		return getNode(nodeId).getDisplayName();
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

	public List<Long> getChildren(long nodeId) {
		Query q = em.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NOT NULL AND n.parent.id = :parentId");
		q.setParameter("parentId", nodeId);
		return (List<Long>) q.getResultList();
	}

	public List<Long> getSiblings(long nodeId) {
		Node node = getNode(nodeId);

		Query q = em.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NOT NULL AND n.parent.id = :parentId AND n.id <> :id");
		q.setParameter("parentId", node.getParent().getId());
		q.setParameter("id", nodeId);

		return (List<Long>) q.getResultList();
	}


	public long getParent(long nodeId) {
		return getNode(nodeId).getParent().getId();
	}

	public boolean exists(long nodeId) {
		return getNode(nodeId) != null;
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setName(long nodeId, String name) {
		Node node = getNode(nodeId);
		node.setName(name);
		em.merge(node);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setDisplayName(long nodeId, String name) {
		Node node = getNode(nodeId);
		node.setDisplayName(name);
		em.merge(node);
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName) {
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


	private void remove(Long curId, String pad) {
		Query q = em.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NOT NULL AND n.parent.id=:parentId");
		q.setParameter("parentId", curId);
		List<Long> children = q.getResultList();
		for (Long n : children) {
			remove(n, pad + "   ");
		}
		System.out.println(pad + curId);
		q = em.createQuery("DELETE FROM Node n WHERE n.id = :id");
		q.setParameter("id", curId);
		q.executeUpdate();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void remove(long nodeId) {
		remove(nodeId, "");
	}


	public List<Long> getToplevelNodes() {
		Query q = em.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NULL");
		return q.getResultList();
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


	private long getNodeId(String name) {
		return getNodeId(name, -1);
	}

	private long getNodeId(String name, long parentId) {
		Query q;

		if (parentId != -1) {
			q = em.createQuery("SELECT n FROM Node n WHERE n.name=:name AND n.parent IS NOT NULL AND n.parent.id=:parentId");
			q.setParameter("name", name);
			q.setParameter("parentId", parentId);
		} else {
			q = em.createQuery("SELECT n FROM Node n WHERE n.name=:name AND n.parent IS NULL");
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
			q = em.createQuery("SELECT n FROM Node n WHERE n.name=:name AND n.parent IS NOT NULL AND n.parent.name=:parent");
			q.setParameter("name", name);
			q.setParameter("parent", parent);
		} else {
			q = em.createQuery("SELECT n FROM Node n WHERE n.name=:name AND n.parent IS NULL");
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

}