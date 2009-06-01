package org.devilry.core.entity;

import java.io.Serializable;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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


@Entity
public class FileDataBlock implements Serializable {

	@Id
	@SequenceGenerator(name = "FILEDATANODE_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILEDATANODE_SEQUENCE")
	private long id;

	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@Column(nullable = false)
	private FileMeta fileMeta;

	@Lob
	@Column(nullable = false)
	private byte[] dataBlock;
	
	private int size;
	
	public FileDataBlock() {
		
	}

	public long getId() {
		return id;
	}
	
	public FileMeta getFileMeta() {
		return fileMeta;
	}

	public void setFileMeta(FileMeta fileMeta) {
		this.fileMeta = fileMeta;
	}
	
	public void setDataBlock(byte[] dataBlock) {
		this.dataBlock = dataBlock;
		size = dataBlock.length;
	}
	
	public byte[] getDataBlock() {
		return dataBlock;
	}
	
	public int getSize() {
		return size;
	}
	
}