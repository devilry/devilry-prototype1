package org.devilry.core.entity;

import javax.persistence.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue("N")
public class Node extends BaseNode {
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "parent")
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