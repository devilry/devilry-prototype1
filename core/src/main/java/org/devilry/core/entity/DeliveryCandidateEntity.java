package org.devilry.core.entity;

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
public class DeliveryCandidateEntity implements Serializable {

    @Id
    @GeneratedValue
    protected long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    protected Collection<FileMetaEntity> files = new LinkedList<FileMetaEntity>();


    public DeliveryCandidateEntity() {
    }


    public long getId() {
        return id;
    }

    public Collection<FileMetaEntity> getFiles() {
		loadFiles();
        return files;
    }


	public void setId(long id) {
		this.id = id;
	}

	public void setFiles(Collection<FileMetaEntity> files) {
		this.files = files;
	}


    /** Force the JPA provider to load the files into memory. This "hack" is
     * required because we use LAZY fetch on the relationship.
     */
    public void loadFiles() {
		// TODO: test if this can be done as
		// getFiles().size() from the bean
        files.size();
    }
}