package org.devilry.core;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import java.util.LinkedList;


@Entity
public class DeliveryCandidateNode implements Serializable {
	@Id
	@GeneratedValue
	protected long id;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	protected Collection<FileNode> files = new LinkedList<FileNode>();;

	public DeliveryCandidateNode() {}


	public long getId() {
		return id;
	}


	public void addFile(FileNode file) {
		files.add(file);
	}

	public void addFile(String path, byte[] data) {
		files.add(new FileNode(this, path, data));
	}


	/** Force the JPA provider to load the files into memory. This "hack" is
	 * required because we use LAZY fetch on the relationship.
	 */
	void loadFiles() {
		files.size();
	}

	public Collection<FileNode> getFiles() {
		return files;
	}
}