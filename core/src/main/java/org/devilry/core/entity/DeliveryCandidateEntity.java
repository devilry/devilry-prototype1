package org.devilry.core.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

@Entity
public class DeliveryCandidateEntity implements Serializable {

    @Id
    @GeneratedValue
    protected long id;

    public DeliveryCandidateEntity() {
    }


    public long getId() {
        return id;
    }

	public void setId(long id) {
		this.id = id;
	}
}