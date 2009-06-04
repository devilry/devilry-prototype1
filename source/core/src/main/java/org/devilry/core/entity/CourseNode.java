package org.devilry.core.entity;

import javax.persistence.*;

import org.devilry.core.entity.*;

@Entity
@DiscriminatorValue("CN")
public class CourseNode extends BaseNode {
	@ManyToOne(fetch = FetchType.LAZY)
	private Node parent;

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}
}