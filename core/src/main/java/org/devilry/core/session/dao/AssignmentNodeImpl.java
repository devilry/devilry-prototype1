package org.devilry.core.session.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.*;

import org.devilry.core.entity.*;

@Stateful
public class AssignmentNodeImpl extends NodeImpl implements AssignmentNodeRemote {
	
	public List<Long> getDeliveryIds() {
		Query q = em.createQuery("SELECT dc.id FROM DeliveryCandidate dc "
				+ "WHERE dc.id = :id");
		q.setParameter("id", node.getId());
		return q.getResultList();
	}
}

