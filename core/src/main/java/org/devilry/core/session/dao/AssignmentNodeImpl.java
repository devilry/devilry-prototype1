package org.devilry.core.session.dao;

import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.Stateless;
import javax.persistence.*;

import org.devilry.core.entity.*;

@Stateless
public class AssignmentNodeImpl extends NodeImpl implements AssignmentNodeRemote {

	public List<Long> getDeliveries(long nodeId) {
		Query q = em.createQuery("SELECT d.id FROM Delivery d WHERE d.assignment.id = :id");
		q.setParameter("id", nodeId);
		return q.getResultList();
	}
}
