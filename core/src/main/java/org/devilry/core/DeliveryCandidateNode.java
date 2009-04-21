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


@Entity
public class DeliveryCandidateNode implements Serializable, Iterable {
	@Transient
    @PersistenceContext(unitName="DevilryCore")
    private EntityManager em;

	@Id
	@GeneratedValue
	protected long id;

	@OneToMany(cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	protected Collection<FileNode> files;

	public void addFile(FileNode file) {
		files.add(file);
		em.persist(file);
	}

	public FileNode getFile(String path) {
		Query q = em.createQuery(
				"SELECT FROM PersistedFile WHERE filePath = :filePath");
		q.setParameter("filePath", path);
		return (FileNode) q.getSingleResult();
	}

	public Iterator iterator() {
		return files.iterator();
	}
}