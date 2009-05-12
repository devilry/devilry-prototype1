package org.devilry.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

/** One block of data in a FileNode.
 *
 * @see FileNode
 * @author Espen Angell Kristiansen <post@espenak.net>
 */
@Entity
public class FileDataNode implements Serializable {

	@Id
	@SequenceGenerator(name = "FILEDATANODE_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILEDATANODE_SEQUENCE")
	private long id;

	@Lob
	@Basic(fetch = FetchType.LAZY, optional=false)
	private byte[] dataBlock;

	public FileDataNode() {
		dataBlock = new byte[0];
	}

	public FileDataNode(byte[] dataBlock) {
		this.dataBlock = dataBlock;
	}

	public void setDataBlock(byte[] data) {
		this.dataBlock = data;
	}

	public byte[] getDataBlock() {
		return dataBlock;
	}
}