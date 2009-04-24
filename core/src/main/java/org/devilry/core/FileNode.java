package org.devilry.core;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;


@Entity
public class FileNode implements Serializable {

	@EmbeddedId
	protected FileNodeId id;

	protected byte[] fileData;

	protected FileNode() {
		id = new FileNodeId();
	}

	public FileNode(DeliveryCandidateNode deliveryCandidate, String path, byte[] data) {
		this();
		id.setDeliveryCandidate(deliveryCandidate);
		id.setFilePath(path);
		fileData = data;
	}
	
	public DeliveryCandidateNode getDirectory() {
		return id.getDeliveryCandidate();
	}

	public String getPath() {
		return id.getFilePath();
	}

	public byte[] getData() {
		return fileData;
	}

	public void setData(byte[] data) {
		fileData = data;
	}
}