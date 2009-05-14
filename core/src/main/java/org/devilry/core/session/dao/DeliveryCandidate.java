package org.devilry.core.session.dao;

import org.devilry.core.entity.FileMetaEntity;
import org.devilry.core.entity.DeliveryCandidateEntity;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateful
public class DeliveryCandidate implements DeliveryCandidateRemote {

	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;
	protected DeliveryCandidateEntity deliveryCandidate = null;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void init(long id) {
		// TODO: should load from db, but create new for now.
		deliveryCandidate = new DeliveryCandidateEntity();
		em.persist(deliveryCandidate);
	}

	public long getId() {
		return deliveryCandidate.getId();
	}
	
	public long getDeliveryId() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long addFile(String filePath) {
		FileMetaEntity f = new FileMetaEntity(deliveryCandidate, filePath);
		em.persist(f);
		em.flush();
		return f.getId();
	}

	public List<Long> getFileIds() {
		// TODO: add this id to query
		Query q = em.createQuery("SELECT f.id FROM FileMetaEntity WHERE f. f ORDER BY f.filePath");
		return q.getResultList();
	}
}