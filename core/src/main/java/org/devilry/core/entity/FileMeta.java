package org.devilry.core.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class FileMeta implements Serializable {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(optional=false)
	private DeliveryCandidate deliveryCandidate;

	private String filePath;

	public FileMeta() {
		
	}
		
	public long getId() {
		return id;
	}

	public DeliveryCandidate getDeliveryCandidate() {
		return deliveryCandidate;
	}

	public String getFilePath() {
		return filePath;
	}


	public void setId(long id) {
		this.id = id;
	}

	public void setDeliveryCandidate(DeliveryCandidate deliveryCandidate) {
		this.deliveryCandidate = deliveryCandidate;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}