package org.devilry.core.session.dao;

import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.devilry.core.entity.FileData;
import org.devilry.core.entity.FileMeta;


/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
@Stateful
public class FileImpl implements FileMetaRemote {
	protected FileMeta fileMeta;
	protected Iterator<Long> readIter = null;

	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;

	public void init(long fileId) {
		resetReadState();
		fileMeta = em.find(FileMeta.class, fileId);
	}

	public long getId() {
		return fileMeta.getId();
	}

	public long getDeliveryCandidateId() {
		return fileMeta.getDeliveryCandidate().getId();
	}

	public String getFilePath() {
		return fileMeta.getFilePath();
	}

	public byte[] read() {
		if(readIter == null) {
			Query q = em.createQuery(
					"SELECT d.id FROM FileData d WHERE d.fileMeta.id = :fileId");
			q.setParameter("fileId", fileMeta.getId());
			List<Long> r = q.getResultList();
			readIter = r.iterator();
		}
		Long id = readIter.next();
		Query q = em.createQuery(
				"SELECT d FROM FileData d WHERE d.id = :id");
		q.setParameter("id", id);
		FileData d = (FileData) q.getSingleResult();
		return d.getDataBlock();
	}

	public void resetReadState() {
		readIter = null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void write(byte[] dataBlock) {
		FileData d = new FileData(fileMeta, dataBlock);
		em.persist(d);
	}
}