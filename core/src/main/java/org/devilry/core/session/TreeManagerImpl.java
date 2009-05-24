package org.devilry.core.session;

import javax.ejb.*;
import javax.persistence.*;
import java.util.Date;
import java.util.Collection;

import org.devilry.core.entity.*;

@Stateless
public class TreeManagerImpl implements TreeManagerRemote {
	@PersistenceContext(unitName="DevilryCore")
	private EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long addNode(String name, String displayName) {
		Node node = new Node();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		em.persist(node);
		em.flush();

		return node.getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long addNode(String name, String displayName, long parentId) {
		Node node = new Node();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		Node parent = getNode(parentId);
		node.setParent(parent);
		Collection<Node> c = parent.getChildren();
		c.add(node);
		parent.setChildren(c);

		em.merge(parent);
		em.flush();

		return node.getId();
	}

	public long addCourseNode(String name, String displayName, long parentId) {
		return addCourseNode(name, name, displayName, parentId);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long addCourseNode(String name, String courseCode, String displayName, long parentId) {
		CourseNode node = new CourseNode();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setCourseCode(courseCode);
		node.setParent(getNode(parentId));

		em.persist(node);
		em.flush();
		
		return node.getId();
	}

	public long addPeriodNode(String name, Date start, Date end, long parentId) {
		return addPeriodNode(name, name, start, end, parentId);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long addPeriodNode(String name, String displayName, Date start, Date end, long parentId) {
		PeriodNode node = new PeriodNode();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setStartDate(start);
		node.setEndDate(end);
		node.setParent(getNode(parentId));

		em.persist(node);
		em.flush();

		return node.getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long addAssignmentNode(String name, String displayName, long parentId) {
		AssignmentNode node = new AssignmentNode();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setParent(getNode(parentId));

		em.persist(node);
		em.flush();

		return node.getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void delNode(long nodeId) {
		Node node = getNode(nodeId);
		em.remove(node);
	}

	public long getNodeIdFromPath(String path) {
		String[] sp = path.split("\\.");

		if(sp.length == 1) {
			return getNodeId(sp[0], -1);
		} else if(sp.length > 1) {
			int length = 0;
			long id = 0;

			while(length < sp.length-1) {
				id = getNodeId(sp[length+1], sp[length]);
				length++;

				if(id == -1)
					return id;
			}

			return id;
		}

		return -1;
	}

	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	private Node getNode(long nodeId) {
		return em.find(Node.class, nodeId);
	}

	private long getNodeId(String name) {
		return getNodeId(name, -1);
	}

	private long getNodeId(String name, long parentId) {
		Query q;
		
		if(parentId != -1) {
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
		} catch(NoResultException e) {
			node = null;
		}

		return node==null?-1:node.getId();
	}

	private long getNodeId(String name, String parent) {
		Query q;
		
		if(parent != null) {
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
		} catch(NoResultException e) {
			node = null;
		}

		return node==null?-1:node.getId();
	}
}

