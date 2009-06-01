package org.devilry.core.session.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.devilry.core.entity.FileDataBlock;
import org.devilry.core.entity.FileMeta;


@Stateless
public class FileDataBlockImpl implements FileDataBlockRemote {
	
	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(long fileMetaId, byte [] data) {
		
		FileMeta fileMeta = em.find(FileMeta.class, fileMetaId);
		
		FileDataBlock fileDataBlock = new FileDataBlock();
		// Set parent
		fileDataBlock.setFileMeta(fileMeta);
		fileDataBlock.setDataBlock(data);				
		
		em.persist(fileDataBlock);
		em.flush();
		
		return fileDataBlock.getId();
	}

	protected FileDataBlock getFileDataBlock(long fileDataBlockId) {
		return em.find(FileDataBlock.class, fileDataBlockId);
	}
	
	public long getFileMeta(long fileDataBlockId) {
		return getFileDataBlock(fileDataBlockId).getFileMeta().getId();
	}

	/*
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
	*/

	public byte[] getFileData(long fileDataBlockId) {
		
		Query q = em.createQuery(
				"SELECT d FROM FileDataBlock d WHERE d.id = :id");
		q.setParameter("id", fileDataBlockId);
		FileDataBlock d = (FileDataBlock) q.getSingleResult();
		return d.getDataBlock();
	}

	public int getSize(long fileDataBlockId) {
		return getFileDataBlock(fileDataBlockId).getSize();
	}

}