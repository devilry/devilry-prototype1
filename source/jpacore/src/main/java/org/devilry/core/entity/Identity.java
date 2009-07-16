package org.devilry.core.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Identity implements Serializable {

	@Id
	public String identity;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	public User user;

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
