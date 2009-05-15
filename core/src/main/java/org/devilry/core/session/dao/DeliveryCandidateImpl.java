package org.devilry.core.session.dao;

import org.devilry.core.entity.FileMeta;
import org.devilry.core.entity.DeliveryCandidate;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateful
public class DeliveryCandidateImpl implements DeliveryCandidateRemote {

	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;
	protected DeliveryCandidate deliveryCandidate = null;

	public void init(long deliveryCandidateId) {
		deliveryCandidate = em.find(DeliveryCandidate.class, deliveryCandidateId);
	}

	public long getId() {
		return deliveryCandidate.getId();
	}

	public long getDeliveryId() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long addFile(String filePath) {
		FileMeta f = new FileMeta(deliveryCandidate, filePath);
		em.persist(f);
		em.flush();
		return f.getId();
	}

	public List<Long> getFileIds() {
		Query q = em.createQuery("SELECT f.id FROM FileMeta f "
				+ "WHERE f.deliveryCandidate.id = :id ORDER BY f.filePath");
		q.setParameter("id", deliveryCandidate.getId());
		return q.getResultList();
	}
}