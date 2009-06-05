package org.devilry.core.dao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.entity.*;

@Stateless
public class PeriodNodeImpl extends BaseNodeImpl implements PeriodNodeRemote,
		PeriodNodeLocal {

	@EJB
	private AssignmentNodeCommon assignmentBean;

	protected PeriodNode getPeriodNode(long nodeId) {
		return getNode(PeriodNode.class, nodeId);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName, Date start, Date end,
			long parentId) {
		PeriodNode node = new PeriodNode();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setStartDate(start);
		node.setEndDate(end);
		node.setCourse(getNode(CourseNode.class, parentId));
		em.persist(node);
		em.flush();
		return node.getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setStartDate(long nodeId, Date start) {
		PeriodNode node = getPeriodNode(nodeId);
		node.setStartDate(start);
		em.merge(node);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setEndDate(long nodeId, Date end) {
		PeriodNode node = getPeriodNode(nodeId);
		node.setEndDate(end);
		em.merge(node);
	}

	public Date getStartDate(long nodeId) {
		return getPeriodNode(nodeId).getStartDate();
	}

	public Date getEndDate(long nodeId) {
		return getPeriodNode(nodeId).getEndDate();
	}

	public List<Long> getAssignments(long periodNodeId) {
		Query q = em.createQuery("SELECT a.id FROM AssignmentNode a "
				+ "WHERE a.period.id = :id");
		q.setParameter("id", periodNodeId);
		List<Long> result = q.getResultList();
		return result;
	}

	public boolean exists(long nodeId) {
		try {
			return getPeriodNode(nodeId) != null;
		} catch (ClassCastException e) {
			return false;
		}
	}

	public long getIdFromPath(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getPath(long nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(long periodId) {

		// Remove childnodes (assignments)
		List<Long> childAssignments = getAssignments(periodId);
		for (Long childAssignmentId : childAssignments) {
			assignmentBean.remove(childAssignmentId);
		}

		// Remove *this* node
		removeNode(periodId, PeriodNode.class);

		// Remove *this* node
		/*
		 * Query q =
		 * em.createQuery("DELETE FROM PeriodNode n WHERE n.id = :id");
		 * q.setParameter("id", periodId); q.executeUpdate();
		 */
	}

	public long getCourse(long periodId) {
		return getPeriodNode(periodId).getCourse().getId();
	}

	//
	// Student
	// ///////////////////////////

	public List<Long> getStudents(long periodId) {
		LinkedList<Long> l = new LinkedList<Long>();
		for (User u : getPeriodNode(periodId).getStudents())
			l.add(u.getId());
		return l;
	}

	public boolean isStudent(long periodId, long userId) {
		return getPeriodNode(periodId).getStudents().contains(getUser(userId));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addStudent(long periodId, long userId) {
		PeriodNode n = getPeriodNode(periodId);
		n.getStudents().add(getUser(userId));
		em.merge(n);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeStudent(long periodId, long userId) {
		PeriodNode n = getPeriodNode(periodId);
		n.getStudents().remove(getUser(userId));
		em.merge(n);
	}

	public List<Long> getPeriodsWhereIsStudent() {
		long userId = userBean.getAuthenticatedUser();
		Query q = em
				.createQuery("SELECT p.id FROM PeriodNode p INNER JOIN p.students stud WHERE stud.id = :userId");
		q.setParameter("userId", userId);
		return q.getResultList();
	}

	//
	// Examiner
	// /////////////////////

	public List<Long> getExaminers(long periodId) {
		LinkedList<Long> l = new LinkedList<Long>();
		for (User u : getPeriodNode(periodId).getExaminers())
			l.add(u.getId());
		return l;
	}

	public boolean isExaminer(long periodId, long userId) {
		return getPeriodNode(periodId).getExaminers().contains(getUser(userId));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addExaminer(long periodId, long userId) {
		PeriodNode n = getPeriodNode(periodId);
		n.getExaminers().add(getUser(userId));
		em.merge(n);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeExaminer(long periodId, long userId) {
		PeriodNode n = getPeriodNode(periodId);
		n.getExaminers().remove(getUser(userId));
		em.merge(n);
	}

	public List<Long> getPeriodsWhereIsExaminer() {
		long userId = userBean.getAuthenticatedUser();
		Query q = em
				.createQuery("SELECT p.id FROM PeriodNode p INNER JOIN p.examiners ex WHERE ex.id = :userId");
		q.setParameter("userId", userId);
		return q.getResultList();
	}

	// ///////////////////
	// Admin
	// ///////////////////
	public List<Long> getPeriodsWhereIsAdmin() {
		return getNodesWhereIsAdmin(PeriodNode.class);
	}

	public void addPeriodAdmin(long periodNodeId, long userId) {
		PeriodNode node = getPeriodNode(periodNodeId);
		addAdmin(node, userId);
	}

	public void removePeriodAdmin(long periodNodeId, long userId) {
		PeriodNode node = getPeriodNode(periodNodeId);
		removeAdmin(node, userId);
	}

	public List<Long> getPeriodAdmins(long periodId) {
		PeriodNode node = getPeriodNode(periodId);
		return getAdmins(node);
	}

	public boolean isPeriodAdmin(long periodId, long userId) {
		PeriodNode courseNode = getPeriodNode(periodId);
		return isAdmin(courseNode, userId);
	}
	
	
	/**
	 * Get period node id, or id of subnode assignment
	 * @param nodePath
	 * @param parentNodeId
	 * @return
	 */
	public long getNodeIdFromPath(String [] nodePath, long parentNodeId) {
		
		long courseid = getPeriodNodeId(nodePath[0], parentNodeId);
		
		if (nodePath.length == 1) {
			return courseid;
		}
		else {
			String [] newNodePath = new String[nodePath.length -1];
			System.arraycopy(nodePath, 1, newNodePath, 0, newNodePath.length);
			
			return assignmentBean.getNodeIdFromPath(newNodePath, courseid);
		}
	}
	
	/**
	 * Get the id of the period node name with parent parentId
	 * @param name
	 * @param parentId
	 * @return
	 */
	protected long getPeriodNodeId(String name, long parentId) {
		Query q;

		if (parentId == -1) {
			return -1;
		} 
		
		q = em.createQuery("SELECT n FROM PeriodNode n WHERE n.name=:name AND n.course IS NOT NULL AND n.course.id=:parentId");
		q.setParameter("name", name);
		q.setParameter("parentId", parentId);

		PeriodNode node;

		try {
			node = (PeriodNode) q.getSingleResult();
		} catch (NoResultException e) {
			node = null;
		}

		return node == null ? -1 : node.getId();
	}
}
