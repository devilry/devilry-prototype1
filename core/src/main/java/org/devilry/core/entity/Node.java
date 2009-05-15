package org.devilry.core.entity;

import javax.persistence.*;

@Entity
@Table(
	name="NODE",
	uniqueConstraints=@UniqueConstraint(columnNames={"name", "parentId"})
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

	@Column(name="parentId")
	protected long parentId;

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

	public long getParentId() {
		return this.parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
}

