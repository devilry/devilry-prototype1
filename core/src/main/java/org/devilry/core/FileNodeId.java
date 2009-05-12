package org.devilry.core;

import java.io.Serializable;
import javax.persistence.Embeddable;


@Embeddable
public class FileNodeId implements Serializable {
    private long deliveryCandidateId;
    private String filePath;

    public FileNodeId() {}

	public FileNodeId(long deliveryCandidateId, String filePath) {
		this.deliveryCandidateId = deliveryCandidateId;
		this.filePath = filePath;
	}

    public long getDeliveryCandidateId() {
        return deliveryCandidateId;
    }

    public String getFilePath() {
        return filePath;
    }


    @Override
    public boolean equals(Object other) {
        if (other instanceof FileNodeId) {
            FileNodeId o = (FileNodeId) other;
            return deliveryCandidateId == o.getDeliveryCandidateId() &&
                    filePath.equals(o.getFilePath());
        }
        return false;
    }

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 37 * hash + (int) (this.deliveryCandidateId ^ (this.deliveryCandidateId >>> 32));
		hash = 37 * hash + (this.filePath != null ? this.filePath.hashCode() : 0);
		return hash;
	}
}