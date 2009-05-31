package org.devilry.core.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AN")
public class AssignmentNode extends Node {
	
	@Temporal(TemporalType.DATE)
	private Date deadline;
	
	public AssignmentNode() {
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
}
