package org.devilry.core.dao;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.devilry.core.daointerfaces.DeliveryCandidateLocal;
import org.devilry.core.daointerfaces.DeliveryLocal;
import org.devilry.core.daointerfaces.DeliveryRemote;
import org.devilry.core.daointerfaces.UserLocal;
import org.devilry.core.entity.AssignmentNode;
import org.devilry.core.entity.Delivery;
import org.devilry.core.entity.User;

@Stateful
public class DeliveryImpl implements DeliveryRemote, DeliveryLocal {
	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;

	@EJB
	private UserLocal userBean;

	@EJB
	private DeliveryCandidateLocal deliveryCandidateBean;

	public DeliveryImpl() {
	}

	private User getUser(long userId) {
		return (User) em.find(User.class, userId);
	}

	private Delivery getDelivery(long deliveryId) {
		return em.find(Delivery.class, deliveryId);
	}

	public long getAssignment(long deliveryId) {
		return getDelivery(deliveryId).getAssignment().getId();
	}

	@SuppressWarnings("unchecked")
	public List<Long> getDeliveryCandidates(long deliveryId) {
		Query q = em.createQuery("SELECT d.id FROM DeliveryCandidate d "
				+ "WHERE d.delivery.id = :id");
		q.setParameter("id", deliveryId);
		return q.getResultList();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(long assignmentId) {
		Delivery d = new Delivery();
		AssignmentNode a = em.find(AssignmentNode.class, assignmentId);
		d.setAssignment(a);
		em.persist(d);
		em.flush();
		return d.getId();
	}

	public long getLastDeliveryCandidate(long deliveryId) {
		Query q = em
				.createQuery("SELECT d.id FROM DeliveryCandidate d WHERE d.timeOfDelivery = MAX(d.timeOfDelivery)");
		return (Long) q.getSingleResult();
	}

	public long getLastValidDeliveryCandidate(long deliveryId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getStatus(long deliveryId) {
		return getDelivery(deliveryId).getStatus();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setStatus(long deliveryId, int status) {
		getDelivery(deliveryId).setStatus(status);
	}

	public boolean exists(long deliveryId) {
		return getDelivery(deliveryId) != null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(long deliveryId) {
		List<Long> children = getDeliveryCandidates(deliveryId);
		for(long dcId: children) {
			deliveryCandidateBean.remove(dcId);
		}
		em.remove(getDelivery(deliveryId));
	}

	//
	// Student
	// ///////////////////////////

	public List<Long> getStudents(long deliveryId) {
		LinkedList<Long> l = new LinkedList<Long>();
		for (User u : getDelivery(deliveryId).getStudents())
			l.add(u.getId());
		return l;
	}

	public boolean isStudent(long deliveryId, long userId) {
		return getDelivery(deliveryId).getStudents().contains(getUser(userId));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addStudent(long deliveryId, long userId) {
		Delivery n = getDelivery(deliveryId);
		n.getStudents().add(getUser(userId));
		em.merge(n);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeStudent(long deliveryId, long userId) {
		Delivery n = getDelivery(deliveryId);
		n.getStudents().remove(getUser(userId));
		em.merge(n);
	}

	@SuppressWarnings("unchecked")
	public List<Long> getDeliveriesWhereIsStudent() {
		long userId = userBean.getAuthenticatedUser();
		Query q = em
				.createQuery("SELECT d.id FROM Delivery d INNER JOIN d.students stud WHERE stud.id = :userId");
		q.setParameter("userId", userId);
		return q.getResultList();
	}

	//
	// Examiner
	// /////////////////////

	public List<Long> getExaminers(long deliveryId) {
		LinkedList<Long> l = new LinkedList<Long>();
		for (User u : getDelivery(deliveryId).getExaminers())
			l.add(u.getId());
		return l;
	}

	public boolean isExaminer(long deliveryId, long userId) {
		return getDelivery(deliveryId).getExaminers().contains(getUser(userId));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void addExaminer(long deliveryId, long userId) {
		Delivery n = getDelivery(deliveryId);
		n.getExaminers().add(getUser(userId));
		em.merge(n);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removeExaminer(long deliveryId, long userId) {
		Delivery n = getDelivery(deliveryId);
		n.getExaminers().remove(getUser(userId));
		em.merge(n);
	}

	@SuppressWarnings("unchecked")
	public List<Long> getDeliveriesWhereIsExaminer() {
		long userId = userBean.getAuthenticatedUser();
		Query q = em
				.createQuery("SELECT d.id FROM Delivery d INNER JOIN d.examiners ex WHERE ex.id = :userId");
		q.setParameter("userId", userId);
		return q.getResultList();
	}
}
