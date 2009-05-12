package org.devilry.core.session;

import org.devilry.core.entity.FileMetaEntity;
import org.devilry.core.entity.DeliveryCandidateEntity;
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
	public long add(DeliveryCandidateEntity dc) {
		em.persist(dc);
		return dc.getId();
	}

	/** Get a DeliveryCandidateNode without files loaded into memory.
	@return A DeliveryCandidateNode where <b>getFiles() do not work</b>. */
	public DeliveryCandidateEntity get(long id) {
		return em.find(DeliveryCandidateEntity.class, id);
	}

	/** Get a DeliveryCandidateNode with files loaded into memory.
	@return A DeliveryCandidateNode where <b>getFiles() work</b>.
	 */
	public DeliveryCandidateEntity getFull(long id) {
		DeliveryCandidateEntity d = em.find(DeliveryCandidateEntity.class, id);
		d.loadFiles();
		return d;
	}

	public FileMetaEntity getFile(DeliveryCandidateEntity deliveryCandidate, String path) {
		Query q = em.createQuery(
				"SELECT n FROM FileNode n WHERE n.id.filePath = :filePath " +
				"AND n.id.deliveryCandidate.id = :pid");
		q.setParameter("filePath", path);
		q.setParameter("pid", deliveryCandidate.getId());
		return (FileMetaEntity) q.getSingleResult();
	}

	public List<String> getFilePaths(DeliveryCandidateEntity deliveryCandidate) {
		Query q = em.createQuery(
				"SELECT f.id.filePath FROM FileNode f WHERE f.id.deliveryCandidate.id = :pid");
		q.setParameter("pid", deliveryCandidate.getId());
		List<String> result = q.getResultList();
		return result;
	}
}