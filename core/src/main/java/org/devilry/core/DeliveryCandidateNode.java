package org.devilry.core;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.GeneratedValue;
import java.util.Iterator;
import java.util.LinkedList;


@Entity
public class DeliveryCandidateNode implements Serializable {
	@Id
	@GeneratedValue
	protected long id;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	protected Collection<FileNode> files;

	public DeliveryCandidateNode() {
		files = new LinkedList<FileNode>();
	}


	public long getId() {
		return id;
	}


	public void addFile(FileNode file) {
		files.add(file);
		//em.persist(file);
	}

	/*
	public FileNode getFile(String path) {
		Query q = em.createQuery(
				"SELECT FROM PersistedFile WHERE filePath = :filePath");
		q.setParameter("filePath", path);
		return (FileNode) q.getSingleResult();
		return null;
	}
	*/

	public Collection<FileNode> getFiles() {
		return files;
	}

	public void addFile(String path, byte[] data) {
		files.add(new FileNode(this, path, data));
	}
}