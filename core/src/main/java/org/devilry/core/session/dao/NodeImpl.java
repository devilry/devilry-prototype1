package org.devilry.core.session.dao;

import javax.ejb.Stateful;
import javax.persistence.*;

@Stateful
public class NodeImpl implements NodeRemote {
	@PersistenceContext(unitName="DevilryCore")
	protected EntityManager em;

	protected org.devilry.core.entity.Node node;

	public boolean init(long nodeId) {
		Query q = em.createQuery("SELECT n FROM Node n WHERE n.id=:nodeId");
		q.setParameter("nodeId", nodeId);

		try {
			node = (org.devilry.core.entity.Node) q.getSingleResult();
		} catch(NoResultException e) {
			node = null;
		}

		return node==null?false:true;
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
		org.devilry.core.entity.Node cn = node;
		String path = null;

		while(true) {
			if(cn.getParent() != null) {
				if(path==null)
					path = cn.getName();
				else
					path = cn.getName() + "." + path;

				cn = cn.getParent();
			} else {
				path = cn.getName() + "." + path;
				break;
			}
		}
		
		return path;
	}
}

