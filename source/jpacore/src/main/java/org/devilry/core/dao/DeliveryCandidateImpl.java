package org.devilry.core.dao;

import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.DeliveryCandidateLocal;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;
import org.devilry.core.daointerfaces.FileDataBlockLocal;
import org.devilry.core.daointerfaces.FileMetaLocal;
import org.devilry.core.entity.Delivery;
import org.devilry.core.entity.FileMeta;
import org.devilry.core.entity.DeliveryCandidate;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;

@Stateless
public class DeliveryCandidateImpl implements DeliveryCandidateRemote, DeliveryCandidateLocal {

	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;
	
	@EJB
	private FileMetaLocal fileMetaBean;
		
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public long create(long deliveryId) {
		DeliveryCandidate candidate = new DeliveryCandidate();
		Delivery delivery = em.find(Delivery.class, deliveryId);
		candidate.setDelivery(delivery);
		candidate.setTimeOfDelivery(new Date());
		em.persist(candidate);
		em.flush();
		
		return candidate.getId();
	}
	
	protected DeliveryCandidate getDeliveryCandidate(long deliveryCandidateId) {
		return em.find(DeliveryCandidate.class, deliveryCandidateId);
	}
	

	public long getDelivery(long deliveryCandidateId) {
		return getDeliveryCandidate(deliveryCandidateId).getDelivery().getId();
	}
	

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public long addFile(long deliveryCandidateId, String filePath) {
		
		DeliveryCandidate deliveryCandidate = getDeliveryCandidate(deliveryCandidateId);
		
		FileMeta fileMeta = new FileMeta();
		fileMeta.setDeliveryCandidate(deliveryCandidate);
		fileMeta.setFilePath(filePath);		
		
		em.persist(fileMeta);
		em.flush();
		return fileMeta.getId();
	}

	@SuppressWarnings("unchecked")
	public List<Long> getFiles(long deliveryCandidateId) {
		Query q = em.createQuery("SELECT f.id FROM FileMeta f "
				+ "WHERE f.deliveryCandidate.id = :id ORDER BY f.filePath");
		q.setParameter("id", deliveryCandidateId);
		return q.getResultList();
	}

	public int getStatus(long deliveryCandidateId) {
		return getDeliveryCandidate(deliveryCandidateId).getStatus();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void setStatus(long deliveryCandidateId, int status) {
		getDeliveryCandidate(deliveryCandidateId).setStatus(status);
	}

	public Date getTimeOfDelivery(long deliveryCandidateId) {
		return getDeliveryCandidate(deliveryCandidateId).getTimeOfDelivery();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(long deliveryCandidateId) throws UnauthorizedException {
		for(long fileMetaId: getFiles(deliveryCandidateId))
			fileMetaBean.remove(fileMetaId);
		em.remove(getDeliveryCandidate(deliveryCandidateId));
	}

	public boolean exists(long deliveryCandidateId) {
		return getDeliveryCandidate(deliveryCandidateId) != null;
	}
}