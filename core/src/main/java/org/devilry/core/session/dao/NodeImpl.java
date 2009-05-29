package org.devilry.core.session.dao;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;
import java.util.List;

import org.devilry.core.entity.*;

@Stateful
public class NodeImpl implements NodeRemote {
	@PersistenceContext(unitName="DevilryCore")
	protected EntityManager em;

	protected Node node;

	public void init(long nodeId) {
		node = em.find(Node.class, nodeId);
	}

	public void setId(long nodeId) {
		node.setId(nodeId);
		em.merge(node);
	}

	public long getId() {
		return node.getId();
	}

	public void setName(String name) {
		node.setName(name);
		em.merge(node);
	}

	public String getName() {
		return node.getName();
	}

	public void setDisplayName(String name) {
		node.setDisplayName(name);
		em.merge(node);
	}

	public String getDisplayName() {
		return node.getDisplayName();
	}

	public String getPath() {
		// need a fresh object to access parent
		Node cn = em.find(Node.class, node.getId());
		String path = null;

		while(true) {
			if(cn.getParent() != null) {
				if(path==null)
					path = cn.getName();
				else
					path = cn.getName() + "." + path;

				cn = cn.getParent();
			} else {
				if(path == null)
					path = cn.getName();
				else
					path = cn.getName() + "." + path;
				break;
			}
		}
		
		return path;
	}

	public List<Long> getChildren() {
		Query q = em.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NOT NULL AND n.parent.id=:parentId");
		q.setParameter("parentId", node.getId());

		return (List<Long>) q.getResultList();
	}

	public List<Long> getSiblings() {
		// need a fresh object to access parent
		node = em.find(Node.class, node.getId());

		Query q = em.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NOT NULL AND n.parent.id=:parentId AND n.id<>:id");
		q.setParameter("parentId", node.getParent().getId());
		q.setParameter("id", node.getId());

		return (List<Long>) q.getResultList();
	}



	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)		
	private void remove(Long curId, String pad) {
		Query q = em.createQuery("SELECT n.id FROM Node n WHERE n.parent IS NOT NULL AND n.parent.id=:parentId");
		q.setParameter("parentId", curId);
		List<Long> children = q.getResultList();
		for(Long n: children) {
			remove(n, pad + "   ");
		}
		System.out.println(pad + curId);
		q = em.createQuery("DELETE FROM Node n WHERE n.id = :id");
		q.setParameter("id", curId);
		q.executeUpdate();
	}

	public void remove() {
		node = em.find(Node.class, node.getId());
		remove(node.getId(), "");
	}
}

