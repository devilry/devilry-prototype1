package org.devilry.core;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class FileNode implements Serializable {

	@Id
	protected DeliveryCandidateNode deliveryCandidate;
	
	@Id
	protected String filePath;

	protected byte[] fileData;

	protected FileNode() {}

	public FileNode(DeliveryCandidateNode directory, String path, byte[] data) {
		deliveryCandidate = directory;
		filePath = path;
		fileData = data;
	}
	
	public DeliveryCandidateNode getDirectory() {
		return deliveryCandidate;
	}

	public String getPath() {
		return filePath;
	}

	public byte[] getData() {
		return fileData;
	}

	public void setData(byte[] data) {
		fileData = data;
	}
}