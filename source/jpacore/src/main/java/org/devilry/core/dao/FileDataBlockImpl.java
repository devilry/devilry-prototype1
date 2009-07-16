package org.devilry.core.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.devilry.core.daointerfaces.FileDataBlockLocal;
import org.devilry.core.daointerfaces.FileDataBlockRemote;
import org.devilry.core.entity.FileDataBlock;
import org.devilry.core.entity.FileMeta;


@Stateless
public class FileDataBlockImpl implements FileDataBlockRemote, FileDataBlockLocal {
	
	@PersistenceContext(unitName = "DevilryCore")
	protected EntityManager em;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
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

	public boolean exists(long fileDataBlockId) {
		return getFileDataBlock(fileDataBlockId) != null;
	}
}