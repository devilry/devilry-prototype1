package org.devilry.core.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

@Entity
public class Delivery implements Serializable {

	@Id
	@GeneratedValue
	protected long id;

	@ManyToOne(optional = false)
	private AssignmentNode assignment;

	protected Delivery() {
	}

	public Delivery(AssignmentNode assignment) {
		this.assignment = assignment;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}