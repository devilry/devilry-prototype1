package org.devilry.core.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FileMetaEntity implements Serializable {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(optional=false)
	private DeliveryCandidateEntity deliveryCandidate;

	private String filePath;

	protected FileMetaEntity() {
	}

	public FileMetaEntity(DeliveryCandidateEntity deliveryCandidate, String filePath) {
		this.deliveryCandidate = deliveryCandidate;
		this.filePath = filePath;
	}

	public long getId() {
		return id;
	}

	public DeliveryCandidateEntity getDeliveryCandidate() {
		return deliveryCandidate;
	}

	public String getFilePath() {
		return filePath;
	}


	public void setId(long id) {
		this.id = id;
	}

	public void setDeliveryCandidate(DeliveryCandidateEntity deliveryCandidate) {
		this.deliveryCandidate = deliveryCandidate;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}