package org.devilry.core.session.dao;

import org.devilry.core.entity.AssignmentNode;
import org.devilry.core.entity.Delivery;
import org.devilry.core.entity.DeliveryCandidate;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Stateful
public class DeliveryImpl implements DeliveryRemote {
	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;

	public DeliveryImpl() {
	}

	protected Delivery getDelivery(long deliveryId) {
		return em.find(Delivery.class, deliveryId);
	}

	public long getAssignment(long deliveryId) {
		return getDelivery(deliveryId).getAssignment().getId();
	}

	
	@SuppressWarnings("unchecked")
	public List<Long> getDeliveryCandidates(long deliveryId) {
		Query q = em.createQuery("SELECT d.id FROM DeliveryCandidate d "
				+ "WHERE d.delivery.id = :id");
		q.setParameter("id", getDelivery(deliveryId).getId());
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
		Query q = em.createQuery("SELECT d.id FROM DeliveryCandidate d WHERE d.timeOfDelivery = MAX(d.timeOfDelivery)");
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

	public List<Long> getCorrectors(long deliveryId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Long> getStudents(long deliveryId) {
		// TODO Auto-generated method stub
		return null;
	}
}
