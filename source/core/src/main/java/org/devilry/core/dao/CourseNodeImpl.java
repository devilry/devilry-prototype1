package org.devilry.core.dao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

import org.devilry.core.NoSuchObjectException;
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
public class CourseNodeImpl extends BaseNodeImpl
		implements CourseNodeRemote, CourseNodeLocal {
	
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

	public List<Long> getPeriods(long courseNodeId) {
		Query q = em.createQuery("SELECT p.id FROM PeriodNode p " +
				"WHERE p.course.id = :id");
		q.setParameter("id", courseNodeId);
		return q.getResultList();
	}
	
	private CourseNode getCourseNode(long courseNodeId) {
		return getNode(CourseNode.class, courseNodeId);
	}

	public boolean exists(long courseId) {
		try {
			return getCourseNode(courseId) != null;
		} catch(ClassCastException e) {
			return false;
		}
	}

	public long getParentNode(long courseNodeId) {
		return getCourseNode(courseNodeId).getParent().getId();
	}

	public List<Long> getCoursesWhereIsAdmin() {
		return getNodesWhereIsAdmin(CourseNode.class);
	}

	public boolean isCourseAdmin(long courseNodeId) throws NoSuchObjectException {
		CourseNode courseNode = getCourseNode(courseNodeId);
		if(isAdmin(courseNode, userBean.getAuthenticatedUser())) {
			return true;
		} else {
			return nodeBean.isNodeAdmin(getParentNode(courseNodeId));
		}
	}

	public void addCourseAdmin(long courseNodeId, long userId) {
		CourseNode node = getCourseNode(courseNodeId);
		addAdmin(node, userId);
	}

	public void removeCourseAdmin(long courseNodeId, long userId) {
		CourseNode node = getCourseNode(courseNodeId);
		removeAdmin(node, userId);
	}
	
	public List<Long> getCourseAdmins(long courseNodeId) {
		CourseNode node = getCourseNode(courseNodeId);
		return getAdmins(node);
	}
	
	public void remove(long courseNodeId) throws NoSuchObjectException {
		// Remove childnodes
		List<Long> childPeriods = getPeriods(courseNodeId);
		for (Long childPeriodId : childPeriods) {
			periodBean.remove(childPeriodId);
		}

		// Remove *this* node
		removeNode(courseNodeId, CourseNode.class);
	}
	
	
	public NodePath getPath(long courseNodeId) throws NoSuchObjectException {
		
		CourseNode course = getCourseNode(courseNodeId);
		String courseName = course.getName();
		
		// Get path from parent node
		NodePath path = nodeBean.getPath(course.getParent().getId());
			
		// Add current node name to path
		path.addToEnd(courseName);
				
		return path;
	}
	
	public long getIdFromPath(NodePath nodePath) throws NoSuchObjectException {
		
		NodePath pathCopy = new NodePath(nodePath);
		String courseName = pathCopy.removeLastPathComponent();
		
		long parentNodeId = nodeBean.getIdFromPath(pathCopy);
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
		
		q = em.createQuery(
				"SELECT n FROM CourseNode n WHERE n.name=:name AND " +
				"n.parent IS NOT NULL AND n.parent.id=:parentId");
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
