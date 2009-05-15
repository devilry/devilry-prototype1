package org.devilry.core.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

@Entity
public class DeliveryCandidate implements Serializable {

	@Id
	@GeneratedValue
	protected long id;

	@ManyToOne(optional = false)
	private Delivery delivery;

	protected DeliveryCandidate() {
	}

	public DeliveryCandidate(Delivery delivery) {
		this.delivery = delivery;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Delivery getDelivery() {
		return delivery;
	}
}