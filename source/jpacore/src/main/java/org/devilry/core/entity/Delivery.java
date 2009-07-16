package org.devilry.core.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Delivery implements Serializable {

	@Id
	@GeneratedValue
	protected long id;
	
	private int status;

	@ManyToOne(optional = false, fetch=FetchType.LAZY)
	private AssignmentNode assignment;
	
	@ManyToMany(cascade={})
	private Set<User> students;

	@ManyToMany(cascade={})
	private Set<User> examiners;

	public Delivery() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public AssignmentNode getAssignment() {
		return assignment;
	}

	public void setAssignment(AssignmentNode assignment) {
		this.assignment = assignment;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setExaminers(Set<User> examiners) {
		this.examiners = examiners;
	}

	public Set<User> getExaminers() {
		return examiners;
	}

	public void setStudents(Set<User> students) {
		this.students = students;
	}

	public Set<User> getStudents() {
		return students;
	}
}