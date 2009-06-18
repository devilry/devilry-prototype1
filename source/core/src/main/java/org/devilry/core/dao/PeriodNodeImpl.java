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

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.CourseNodeCommon;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.entity.*;

@Stateless
public class PeriodNodeImpl extends BaseNodeImpl implements PeriodNodeRemote,
		PeriodNodeLocal {

	@EJB(beanInterface = AssignmentNodeLocal.class)
	private AssignmentNodeCommon assignmentBean;

	@EJB(beanInterface = CourseNodeLocal.class)
	private CourseNodeCommon courseBean;

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

	public void remove(long periodId) throws NoSuchObjectException {

		// Remove childnodes (assignments)
		List<Long> childAssignments = getAssignments(periodId);
		for (Long childAssignmentId : childAssignments) {
			assignmentBean.remove(childAssignmentId);
		}

		// Remove *this* node
		removeNode(periodId, PeriodNode.class);
	}

	public long getParentCourse(long periodId) {
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

	public boolean isStudent(long periodId) {
		return getPeriodNode(periodId).getStudents().contains(
				getUser(userBean.getAuthenticatedUser()));
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
		Query q = em.createQuery("SELECT p.id FROM PeriodNode p "
				+ "INNER JOIN p.students stud WHERE stud.id = :userId");
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

	public boolean isExaminer(long periodId) {
		return getPeriodNode(periodId).getExaminers().contains(
				getUser(userBean.getAuthenticatedUser()));
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
		Query q = em.createQuery("SELECT p.id FROM PeriodNode p "
				+ "INNER JOIN p.examiners ex WHERE ex.id = :userId");
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

	public boolean isPeriodAdmin(long periodId) throws NoSuchObjectException,
			UnauthorizedException {
		PeriodNode periodNode = getPeriodNode(periodId);
		if (isAdmin(periodNode, userBean.getAuthenticatedUser())) {
			return true;
		} else {
			return courseBean.isCourseAdmin(getParentCourse(periodId));
		}
	}

	public long getIdFromPath(NodePath nodePath) throws NoSuchObjectException {

		NodePath pathCopy = new NodePath(nodePath);
		String periodName = pathCopy.removeLastPathComponent();

		long parentNodeId = courseBean.getIdFromPath(pathCopy);
		long periodId = getPeriodNodeId(periodName, parentNodeId);

		return periodId;
	}

	/**
	 * Get the id of the period node name with parent parentId
	 * 
	 * @param name
	 * @param parentId
	 * @return
	 */
	protected long getPeriodNodeId(String name, long parentId) {
		Query q;

		if (parentId == -1) {
			return -1;
		}

		q = em
				.createQuery("SELECT n FROM PeriodNode n WHERE n.name=:name AND n.course IS NOT NULL AND n.course.id=:parentId");
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

	public NodePath getPath(long periodId) throws NoSuchObjectException {

		PeriodNode period = getPeriodNode(periodId);
		String periodName = period.getName();

		// Get path from parent course
		NodePath path = courseBean.getPath(period.getCourse().getId());

		// Add current node name to path
		path.addToEnd(periodName);

		return path;
	}

	/**
	 * Get the id of the period node name with parent parentId
	 * 
	 * @param name
	 * @param parentId
	 * @return
	 */
	/*
	 * protected String getPeriodNodeName(long periodId) {
	 * getPeriodNode(periodId); }
	 */
}
