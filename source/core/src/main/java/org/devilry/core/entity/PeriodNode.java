package org.devilry.core.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue("PN")
public class PeriodNode extends Node {
	@ManyToOne(fetch=FetchType.LAZY)
	@Column(name="parent")
	private CourseNode course;


	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@ManyToMany(cascade={})
	private Set<User> students;

	@ManyToMany(cascade={})
	private Set<User> examiners;
	
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

	public void setExaminers(Set<User> examiners) {
		this.examiners = examiners;
	}

	public Set<User> getExaminers() {
		return examiners;
	}

	public void setCourse(CourseNode course) {
		this.course = course;
	}

	public CourseNode getCourse() {
		return course;
	}
}

