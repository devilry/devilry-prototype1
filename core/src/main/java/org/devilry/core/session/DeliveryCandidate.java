package org.devilry.core.session;

import java.util.Collection;
import org.devilry.core.entity.FileMetaEntity;
import org.devilry.core.entity.DeliveryCandidateEntity;
import java.util.List;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.devilry.core.digest.FileMetaDigest;

@Stateful
public class DeliveryCandidate implements DeliveryCandidateRemote {

	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;
	protected DeliveryCandidateEntity deliveryCandidate;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void init(long id) {
		// TODO: should load from db, but create new for now.
		em.persist(new DeliveryCandidateEntity());
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long addFile(String filePath) {
		FileMetaEntity f = new FileMetaEntity(deliveryCandidate, filePath);
		em.persist(f);
		return f.getId();
	}

	public List<Long> getFileIds() {
		Query q = em.createQuery("SELECT f.id FROM FileMetaEntity f ORDER BY f.filePath");
		return q.getResultList();
	}

	public FileMetaDigest getFileMeta(long fileId) {
		Query q = em.createQuery("SELECT f FROM FileMetaEntity f " +
				"WHERE id = :fileId");
		q.setParameter("fileId", fileId);
		FileMetaEntity f = (FileMetaEntity) q.getSingleResult();
		if (f == null) {
			return null;
		}
		return new FileMetaDigest(f);
	}

	public FileMetaDigest getFileMeta(String filePath) {
		Query q = em.createQuery("SELECT f FROM FileMetaEntity f " +
				"WHERE filePath = :filePath");
		q.setParameter("filePath", filePath);
		FileMetaEntity f = (FileMetaEntity) q.getSingleResult();
		if (f == null) {
			return null;
		}
		return new FileMetaDigest(f);
	}

	public Collection<FileMetaDigest> getFileMetas() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}