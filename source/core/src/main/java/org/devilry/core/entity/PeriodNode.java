package org.devilry.core.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@DiscriminatorValue("PN")
public class PeriodNode extends Node {
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	public PeriodNode() {
	
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date start) {
		this.startDate = start;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date end) {
		this.endDate = end;
	}
}

