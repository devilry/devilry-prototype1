package org.devilry.core.session.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.Stateless;
import javax.persistence.*;

import org.devilry.core.entity.*;

@Stateless
public class AssignmentNodeImpl extends NodeImpl implements AssignmentNodeRemote {

	@SuppressWarnings("unchecked")
	public List<Long> getDeliveries(long nodeId) {
		Query q = em.createQuery("SELECT d.id FROM Delivery d WHERE d.assignment.id = :id");
		q.setParameter("id", nodeId);
		return q.getResultList();
	}

	public Date getDeadline(long assignmentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDeadline(long assignmentId, Date date) {
		// TODO Auto-generated method stub
		
	}
}
