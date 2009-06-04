package org.devilry.core.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.entity.*;

@Stateless
public class PeriodNodeImpl extends BaseNodeImpl implements PeriodNodeRemote,
		PeriodNodeLocal {
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
				+ "WHERE a.parent.id = :id");
		q.setParameter("id", periodNodeId);
		return q.getResultList();
	}


	public boolean exists(long nodeId) {
		try {
			return getPeriodNode(nodeId) != null;
		} catch(ClassCastException e) {
			return false;
		}
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

	public long getCourse(long periodId) {
		return getPeriodNode(periodId).getCourse().getId();
	}

	public List<Long> getPeriodsWhereIsAdmin() {
		return getNodesWhereIsAdmin(PeriodNode.class);
	}

	public long getIdFromPath(String path) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getPath(long nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(long nodeId) {
		// TODO Auto-generated method stub
		
	}
}
