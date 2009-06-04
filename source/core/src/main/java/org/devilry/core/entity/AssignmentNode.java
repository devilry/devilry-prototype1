package org.devilry.core.entity;

import java.util.Date;

import javax.persistence.*;

@Entity
@DiscriminatorValue("AN")
public class AssignmentNode extends BaseNode {

	@ManyToOne(fetch = FetchType.LAZY)
	private PeriodNode period;

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

	public void setPeriod(PeriodNode period) {
		this.period = period;
	}

	public PeriodNode getPeriod() {
		return period;
	}
}
