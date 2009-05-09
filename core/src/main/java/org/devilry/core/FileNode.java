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
    }

    public FileNode(long deliveryCandidateId, String path, byte[] data) {
		id = new FileNodeId(deliveryCandidateId, path);
        fileData = data;
    }

	public FileNodeId getId() {
		return id;
	}

    public byte[] getData() {
        return fileData;
    }

    public void setData(byte[] data) {
        fileData = data;
    }
}