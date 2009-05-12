package org.devilry.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


@Entity
public class FileNode implements Serializable {

    @EmbeddedId
    private FileNodeId id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Collection<FileDataNode> dataBlocks = new LinkedList<FileDataNode>();

    protected FileNode() {
    }

    public FileNode(long deliveryCandidateId, String path) {
		id = new FileNodeId(deliveryCandidateId, path);
    }

	public FileNodeId getId() {
		return id;
	}

	public Collection<FileDataNode> getDataBlocks() {
		return dataBlocks;
	}

	public void addDataBlock(FileDataNode dataBlock) {
		dataBlocks.add(dataBlock);
	}
}