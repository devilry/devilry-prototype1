package org.devilry.core.entity;

import javax.persistence.*;

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

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="parent", nullable=true)
	protected Node parent;

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
}

