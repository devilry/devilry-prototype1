package org.devilry.core.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.devilry.core.daointerfaces.FileMetaRemote;
import org.devilry.core.entity.Delivery;
import org.devilry.core.entity.DeliveryCandidate;
import org.devilry.core.entity.FileDataBlock;
import org.devilry.core.entity.FileMeta;

@Stateless
public class FileMetaImpl implements FileMetaRemote {

	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;

	public long create(long deliveryCandidateId, String filePath) {

		DeliveryCandidate deliveryCandidate = em.find(DeliveryCandidate.class, deliveryCandidateId);

		FileMeta fileMeta = new FileMeta();
		// Set parent
		fileMeta.setDeliveryCandidate(deliveryCandidate);
		fileMeta.setFilePath(filePath);

		em.persist(fileMeta);
		em.flush();

		return fileMeta.getId();
	}

	protected FileMeta getFileMeta(long fileMetaId) {
		return em.find(FileMeta.class, fileMetaId);
	}

	public long getDeliveryCandidate(long fileMetaId) {
		return getFileMeta(fileMetaId).getDeliveryCandidate().getId();
	}

	public String getFilePath(long fileMetaId) {
		return getFileMeta(fileMetaId).getFilePath();
	}

	public List<Long> getFileDataBlocks(long fileMetaId) {

		FileMeta fileMeta = getFileMeta(fileMetaId);

		Query q = em.createQuery(
				"SELECT d.id FROM FileDataBlock d WHERE d.fileMeta.id = :fileId");
		q.setParameter("fileId", fileMeta.getId());
		List<Long> r = q.getResultList();

		return r;
	}

	public int getSize(long fileMetaId) {

		FileMeta fileMeta = getFileMeta(fileMetaId);

		Query q = em.createQuery(
				"SELECT SUM(b.size) FROM FileDataBlock b WHERE b.fileMeta.id = :fileMetaId");
		q.setParameter("fileMetaId", fileMeta.getId());

		long size = (Long) q.getSingleResult();

		return (int) size;
	}

	public boolean exists(long fileMetaId) {
		return getFileMeta(fileMetaId) != null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void remove(long fileMetaId) {
		em.remove(getFileMeta(fileMetaId));
	}
}
