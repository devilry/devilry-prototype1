package org.devilry.core;

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

	FileNode fileNode;
	Iterator<FileDataNode> dataIterator;

	public FileReaderBean() {
		fileNode = null;
	}

	public void open(FileNode fileNode) {
		this.fileNode = fileNode;
	}

	public byte[] read() {
		if(dataIterator.hasNext())
			return dataIterator.next().getDataBlock();
		else
			return null;
	}
}