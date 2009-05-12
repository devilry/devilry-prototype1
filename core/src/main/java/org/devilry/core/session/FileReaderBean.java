package org.devilry.core.session;

import org.devilry.core.entity.FileMetaEntity;
import org.devilry.core.entity.FileDataEntity;
import org.devilry.core.*;
import java.util.Iterator;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
@Stateful
public class FileReaderBean implements FileReaderBeanRemote {

	@PersistenceContext(unitName = "DevilryCore")
	private EntityManager em;

	FileMetaEntity fileNode;
	Iterator<FileDataEntity> dataIterator;

	public FileReaderBean() {
		fileNode = null;
	}

	public void open(FileMetaEntity fileNode) {
		this.fileNode = fileNode;
	}

	public byte[] read() {
		if(dataIterator.hasNext())
			return dataIterator.next().getDataBlock();
		else
			return null;
	}
}