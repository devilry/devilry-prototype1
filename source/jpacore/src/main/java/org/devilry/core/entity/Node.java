package org.devilry.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;	
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name", "parent"}))
public class Node extends BaseNode {
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name="parent")
	private Node parent;

	public Node() {
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}
}