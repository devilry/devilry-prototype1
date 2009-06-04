package org.devilry.core.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AN")
public class AssignmentNode extends BaseNode {

	@Temporal(TemporalType.TIMESTAMP)
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
