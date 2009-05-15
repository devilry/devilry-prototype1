package org.devilry.core.session.dao;

import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;

import org.devilry.core.entity.*;

@Stateful
public class AssignmentNodeImpl extends NodeImpl implements AssignmentNodeRemote {
	
	public List<Long> getDeliveryIds() {
		Query q = em.createQuery("SELECT d.id FROM Delivery d "
				+ "WHERE d.assignment.id = :id");
		q.setParameter("id", node.getId());
		return q.getResultList();
	}


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)	
	public long addDelivery() {
		Delivery d = new Delivery((AssignmentNode) node);
		em.persist(d);
		em.flush();
		return d.getId();
	}
}