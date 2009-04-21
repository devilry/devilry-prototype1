package org.devilry.core;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless(mappedName="Delivery")
public class DevieryBean implements DeliveryRemote {
    @PersistenceContext(unitName="DevilryCore")
    private EntityManager em;

	public long add(DeliveryCandidateNode dc) {
		for(FileNode f: dc.getFiles())
			em.persist(f);
		em.persist(dc);
		return dc.getId();
	}
}