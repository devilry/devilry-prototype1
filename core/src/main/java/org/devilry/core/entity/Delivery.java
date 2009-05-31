package org.devilry.core.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
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

	@ManyToOne(optional = false)
	private AssignmentNode assignment;
	
	@ManyToMany
	private Collection<User> students;

	@ManyToMany
	private Collection<User> correctors;

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

	public Collection<User> getStudents() {
		return students;
	}

	public void setStudents(Collection<User> students) {
		this.students = students;
	}

	public Collection<User> getCorrectors() {
		return correctors;
	}

	public void setCorrectors(Collection<User> correctors) {
		this.correctors = correctors;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}