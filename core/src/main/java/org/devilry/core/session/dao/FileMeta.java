package org.devilry.core.session.dao;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.devilry.core.entity.FileMetaEntity;


/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
@Stateful
public class FileMeta implements FileMetaRemote {
	protected FileMetaEntity fileMeta;

	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;

	public void init(long fileId) {
		fileMeta = em.find(FileMetaEntity.class, fileId);
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
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void resetReadState() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void write(byte[] dataBlock) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}