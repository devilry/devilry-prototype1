package org.devilry.core;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DeliveryBean implements DeliveryBeanRemote {

	@PersistenceContext(unitName = "DevilryCore")
	private EntityManager em;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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

	public FileNode getFile(DeliveryCandidateNode deliveryCandidate, String path) {
		Query q = em.createQuery(
				"SELECT n FROM FileNode n WHERE n.id.filePath = :filePath " +
				"AND n.id.deliveryCandidate.id = :pid");
		q.setParameter("filePath", path);
		q.setParameter("pid", deliveryCandidate.getId());
		return (FileNode) q.getSingleResult();
	}

	public List<String> getFilePaths(DeliveryCandidateNode deliveryCandidate) {
		Query q = em.createQuery(
				"SELECT f.id.filePath FROM FileNode f WHERE f.id.deliveryCandidate.id = :pid");
		q.setParameter("pid", deliveryCandidate.getId());
		List<String> result = q.getResultList();
		return result;
	}
}