package org.devilry.core.dao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

import org.devilry.core.NodePath;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.CourseNodeRemote;
import org.devilry.core.daointerfaces.NodeCommon;
import org.devilry.core.daointerfaces.NodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.entity.*;

import java.util.LinkedList;
import java.util.List;

@Stateless
public class CourseNodeImpl extends BaseNodeImpl implements CourseNodeRemote, CourseNodeLocal {
	
	@EJB(beanInterface=PeriodNodeLocal.class) 
	private PeriodNodeCommon periodBean;

	@EJB(beanInterface=NodeLocal.class) 
	private NodeCommon nodeBean;

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

	public long getParentNode(long courseId) {
		return getCourseNode(courseId).getParent().getId();
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
	
	
	public NodePath getPath(long courseNodeId) {
		
		CourseNode course = getCourseNode(courseNodeId);
		String courseName = course.getName();
		
		// Get path from parent node
		NodePath path = nodeBean.getPath(course.getParent().getId());
			
		// Add current node name to path
		path.addToEnd(courseName);
				
		return path;
	}
	
	public long getIdFromPath(NodePath nodePath) {
		
		NodePath pathCopy = new NodePath(nodePath);
		
		String courseName = pathCopy.removeLastPathComponent();
		long parentNodeId = nodeBean.getIdFromPath(nodePath);
		long courseId = getCourseNodeId(courseName, parentNodeId);
		
		return courseId;
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
