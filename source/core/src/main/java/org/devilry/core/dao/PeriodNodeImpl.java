package org.devilry.core.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

import org.devilry.core.daointerfaces.PeriodNodeLocal;
import org.devilry.core.daointerfaces.PeriodNodeRemote;
import org.devilry.core.entity.*;

@Stateless
public class PeriodNodeImpl extends BaseNodeImpl implements PeriodNodeRemote, PeriodNodeLocal {
	protected PeriodNode getPeriodNode(long nodeId) {
		return (PeriodNode) getNode(nodeId);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public long create(String name, String displayName, Date start, Date end, long parentId) {
		PeriodNode node = new PeriodNode();
		node.setName(name.toLowerCase());
		node.setDisplayName(displayName);
		node.setStartDate(start);
		node.setEndDate(end);
		node.setParent(getNode(parentId));
		em.persist(node);
		em.flush();
		return node.getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setStartDate(long nodeId, Date start) {
		PeriodNode node = getPeriodNode(nodeId);
		node.setStartDate(start);
		em.merge(node);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void setEndDate(long nodeId, Date end) {
		PeriodNode node = getPeriodNode(nodeId);
		node.setEndDate(end);
		em.merge(node);
	}


	public Date getStartDate(long nodeId) {
		return getPeriodNode(nodeId).getStartDate();
	}

	public Date getEndDate(long nodeId) {
		return getPeriodNode(nodeId).getEndDate();
	}

	public List<Long> getAssignments(long periodNodeId) {
		Query q = em.createQuery("SELECT a.id FROM AssignmentNode a "
				+ "WHERE a.parent.id = :id");
		q.setParameter("id", periodNodeId);
		return q.getResultList();
	}
}
