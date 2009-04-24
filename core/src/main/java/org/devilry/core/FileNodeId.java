package org.devilry.core;

import java.io.Serializable;
import javax.persistence.Embeddable;


@Embeddable
public class FileNodeId implements Serializable {
	protected DeliveryCandidateNode deliveryCandidate;
	protected String filePath;


	public DeliveryCandidateNode getDeliveryCandidate() {
		return deliveryCandidate;
	}

	public void setDeliveryCandidate(DeliveryCandidateNode deliveryCandidate) {
		this.deliveryCandidate = deliveryCandidate;
	}



	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
