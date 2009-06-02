package org.devilry.core.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue("PN")
public class PeriodNode extends Node {
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@ManyToMany(cascade={})
	private Set<User> students;
	
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
	
	public void setStudents(Set<User> students) {
		this.students = students;
	}

	public Set<User> getStudents() {
		return students;
	}
}

