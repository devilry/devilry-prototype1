package org.devilry.core.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FileMetaEntity implements Serializable {

	@Id
	@ManyToOne
	DeliveryCandidateEntity deliveryCandidate;
	
	@Id
	String filePath;

	protected FileMetaEntity() {
	}

	public FileMetaEntity(DeliveryCandidateEntity deliveryCandidate, String filePath) {
		this.deliveryCandidate = deliveryCandidate;
		this.filePath = filePath;
	}

	public DeliveryCandidateEntity getDeliveryCandidate() {
		return deliveryCandidate;
	}

	public String getFilePath() {
		return filePath;
	}


	public void setDeliveryCandidate(DeliveryCandidateEntity deliveryCandidate) {
		this.deliveryCandidate = deliveryCandidate;
	}

	public void setFilePath() {
		this.filePath = filePath;
	}
}