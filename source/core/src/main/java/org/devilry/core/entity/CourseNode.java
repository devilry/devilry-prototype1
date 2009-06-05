package org.devilry.core.entity;

import javax.persistence.*;

import org.devilry.core.entity.*;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name", "parent"}))
public class CourseNode extends BaseNode {
	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name="parent")
	private Node parent;

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}
}