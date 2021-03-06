package org.devilry.core.entity;

import java.util.Date;
import java.util.Set;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DeliveryCandidate implements Serializable {

	@Id
	@GeneratedValue
	protected long id;

	private int status;
	
	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	private Delivery delivery;

	@Temporal(TemporalType.TIMESTAMP)
	private Date timeOfDelivery;
	
	public DeliveryCandidate() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTimeOfDelivery() {
		return this.timeOfDelivery;
	}

	public void setTimeOfDelivery(Date timeOfDelivery) {
		this.timeOfDelivery = timeOfDelivery;
	}

	public Delivery getDelivery() {
		return delivery;
	}
	
	public void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}