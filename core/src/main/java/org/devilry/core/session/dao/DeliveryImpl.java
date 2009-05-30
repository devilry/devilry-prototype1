package org.devilry.core.session.dao;

import org.devilry.core.entity.Delivery;
import org.devilry.core.entity.DeliveryCandidate;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

//@Stateful
//public class DeliveryImpl implements DeliveryRemote {
//	@PersistenceContext(unitName = "DevilryCore")
//	protected EntityManager em;
//
//	protected Delivery delivery;
//
//	public DeliveryImpl() {
//	}
//
//	public void init(long deliveryId) {
//		delivery = em.find(Delivery.class, deliveryId);
//	}
//
//	public long getId() {
//		return delivery.getId();
//	}
//
//	public long getAssignmentId() {
//		return delivery.getAssignment().getId();
//	}
//
//	public List<Long> getDeliveryCandidateIds() {
//		Query q = em.createQuery("SELECT d.id FROM DeliveryCandidate d "
//				+ "WHERE d.delivery.id = :id");
//		q.setParameter("id", delivery.getId());
//		return q.getResultList();
//	}
//
//	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//	public long addDeliveryCandidate() {
//		DeliveryCandidate d = new DeliveryCandidate(delivery);
//		em.persist(d);
//		em.flush();
//		return d.getId();
//	}
//}
