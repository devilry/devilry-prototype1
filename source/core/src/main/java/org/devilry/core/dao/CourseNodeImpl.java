package org.devilry.core.dao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.entity.*;

import java.util.LinkedList;
import java.util.List;

@Stateless
public class CourseNodeImpl extends BaseNodeImpl implements CourseNodeRemote, CourseNodeLocal {
	
	@EJB
	private PeriodNodeLocal periodBean;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName, long parentId) {
		CourseNode node = new CourseNode();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setParent(getNode(Node.class, parentId));
		em.persist(node);
		em.flush();
		return node.getId();
	}

	@SuppressWarnings("unchecked")
	public List<Long> getAllCourses() {
		Query q = em.createQuery("SELECT c.id FROM CourseNode c");
		return q.getResultList();
	}

	public List<Long> getPeriods(long courseId) {
		Query q = em.createQuery("SELECT p.id FROM PeriodNode p WHERE p.course.id = :id");
		q.setParameter("id", courseId);
		return q.getResultList();
	}
	
	private CourseNode getCourseNode(long courseId) {
		return getNode(CourseNode.class, courseId);
	}

	public boolean exists(long courseId) {
		try {
			return getCourseNode(courseId) != null;
		} catch(ClassCastException e) {
			return false;
		}
	}

	public long getParent(long courseId) {
		return getCourseNode(courseId).getParent().getId();
	}

	public long getIdFromPath(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getPath(long nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Long> getCoursesWhereIsAdmin() {
		return getNodesWhereIsAdmin(CourseNode.class);
	}

	public boolean isCourseAdmin(long nodeId, long userId) {
		CourseNode courseNode = getCourseNode(nodeId);
		return isAdmin(courseNode, userId);
	}
	
	public void addCourseAdmin(long courseNodeId, long userId) {
		CourseNode node = getCourseNode(courseNodeId);
		addAdmin(node, userId);
	}
		
	public void removeCourseAdmin(long courseNodeId, long userId) {
		CourseNode node = getCourseNode(courseNodeId);
		removeAdmin(node, userId);
	}
	
	public List<Long> getCourseAdmins(long courseId) {
		CourseNode node = getCourseNode(courseId);
		return getAdmins(node);
	}
	
	public void remove(long courseId) {
		// Remove childnodes
		List<Long> childPeriods = getPeriods(courseId);
		for (Long childPeriodId : childPeriods) {
			periodBean.remove(childPeriodId);
		}

		// Remove *this* node
		removeNode(courseId, CourseNode.class);
	}
	
	
	
	public long getNodeIdFromPath(String [] nodePath, long parentNodeId) {
		
		long courseid = getCourseNodeId(nodePath[0], parentNodeId);
		
		if (nodePath.length == 1) {
			return courseid;
		}
		else {
			String [] newNodePath = new String[nodePath.length -1];
			System.arraycopy(nodePath, 1, newNodePath, 0, newNodePath.length);
			
			return periodBean.getNodeIdFromPath(newNodePath, courseid);
		}
	}

	
	/**
	 * Get the id of the course node name with parent parentId
	 * @param name
	 * @param parentId
	 * @return
	 */
	protected long getCourseNodeId(String name, long parentId) {
		Query q;

		if (parentId == -1) {
			return -1;
		} 
		
		q = em.createQuery("SELECT n FROM CourseNode n WHERE n.name=:name AND n.parent IS NOT NULL AND n.parent.id=:parentId");
		q.setParameter("name", name);
		q.setParameter("parentId", parentId);

		CourseNode node;

		try {
			node = (CourseNode) q.getSingleResult();
		} catch (NoResultException e) {
			node = null;
		}

		return node == null ? -1 : node.getId();
	}
}
