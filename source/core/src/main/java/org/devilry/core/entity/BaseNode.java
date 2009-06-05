package org.devilry.core.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseNode implements java.io.Serializable {
	@Id
	@GeneratedValue
	@Column(name="id")
	private long id;

	@Column(name = "name")
	private String name;

	private String displayName;

	@ManyToMany(cascade = {})
	private Set<User> admins;

	public BaseNode() {

	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setAdmins(Set<User> admins) {
		this.admins = admins;
	}

	public Set<User> getAdmins() {
		return admins;
	}
}