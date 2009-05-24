package org.devilry.core.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
@Table(
	name="NODE",
	uniqueConstraints=@UniqueConstraint(columnNames={"name", "parent"})
)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="nodeType", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("N")
public class Node implements java.io.Serializable {
	@Id
	@GeneratedValue
	protected long id;

	@Column(name="name")
	protected String name;

	protected String displayName;

	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@Column(name="parent")
	protected Node parent;


	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected Collection<Node> children = new LinkedList<Node>();

	public Node() {

	}

	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return this.id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public Node getParent() {
		return this.parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Collection<Node> getChildren() {
		return children;
	}

	public void setChildren(Collection<Node> children) {
		this.children = children;
	}

	public void addChild(Node child) {
		child.setParent(this);
		children.add(child);
	}
}

