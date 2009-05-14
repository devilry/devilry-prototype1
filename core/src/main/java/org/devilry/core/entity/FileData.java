package org.devilry.core.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/** One block of data in a FileNode.
 *
 * @see FileNode
 * @author Espen Angell Kristiansen <post@espenak.net>
 *
 * @note This is not intergrated with FileMetaEntity yet!
 */
@Entity
public class FileData implements Serializable {

	@Id
	@SequenceGenerator(name = "FILEDATANODE_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILEDATANODE_SEQUENCE")
	private long id;

	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	@Column(nullable = false)
	private FileMeta fileMeta;

	@Lob
	@Column(nullable = false)
	private byte[] dataBlock;

	protected FileData() {
	}

	public FileData(FileMeta fileMeta, byte[] dataBlock) {
		this.fileMeta = fileMeta;
		this.dataBlock = dataBlock;
	}

	public byte[] getDataBlock() {
		return dataBlock;
	}
}