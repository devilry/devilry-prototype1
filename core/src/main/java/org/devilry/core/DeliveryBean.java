package org.devilry.core;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class DeliveryBean implements DeliveryRemote {
    @PersistenceContext(unitName="DevilryCore")
    private EntityManager em;

	public long add(DeliveryCandidateNode dc) {
		em.persist(dc);
		return dc.getId();
	}

	/** Get a DeliveryCandidateNode without files loaded into memory.
	@return A DeliveryCandidateNode where <b>getFiles() do not work</b>. */
	public DeliveryCandidateNode get(long id) {
		return em.find(DeliveryCandidateNode.class, id);
	}

	/** Get a DeliveryCandidateNode with files loaded into memory.
	 @return A DeliveryCandidateNode where <b>getFiles() work</b>.
	 */
	public DeliveryCandidateNode getFull(long id) {
		DeliveryCandidateNode d = em.find(DeliveryCandidateNode.class, id);
		d.loadFiles();
		return d;
	}


	/*
	public FileNode getFile(DeliveryCandidateNode deliveryCandidate, String path) {
		Query q = em.createQuery(
				"SELECT FROM PersistedFile WHERE filePath = :filePath "+
				"AND deliveryCandidate = :deliveryCandidate");
		q.setParameter("filePath", path);
		q.setParameter("deliveryCandidate", deliveryCandidate);
		return (FileNode) q.getSingleResult();
	}
	 * */
}