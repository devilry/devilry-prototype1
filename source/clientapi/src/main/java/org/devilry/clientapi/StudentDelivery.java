package org.devilry.clientapi;

import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;

public class StudentDelivery extends AbstractDelivery {

	DeliveryCommon delivery;
	long deliveryId;
	
	StudentDelivery(long deliveryId, DevilryConnection connection) {
		super(deliveryId, connection);
		this.deliveryId = deliveryId;
	}
	
	protected DeliveryCommon getDeliveryBean() throws NamingException {
		return delivery == null ? delivery = connection.getDelivery() : delivery;
	}
	
	public StudentDeliveryCandidate createDeliveryCandidate() throws NamingException {
		
		DeliveryCandidateCommon deliveryCandidate = connection.getDeliveryCandidate();
		
		long candidateId = deliveryCandidate.create(deliveryId);
		StudentDeliveryCandidate candidate = new StudentDeliveryCandidate(candidateId, connection);
		
		return candidate;
	}
	
	public List<StudentDeliveryCandidate> getDeliveryCandidates() throws NamingException {
		
		List<Long> candidates = getDeliveryBean().getDeliveryCandidates(deliveryId);
		
		LinkedList<StudentDeliveryCandidate> candidateList = new LinkedList<StudentDeliveryCandidate>();
		
		for (long id : candidates) {
			candidateList.add(new StudentDeliveryCandidate(id, connection));
		}
		
		return candidateList;
	}
	
	
	public AbstractDeliveryCandidate getDeliveryCandidate() {
		return null;
	}
	
	public long getDeliveryId() {
		return deliveryId;
	}
}
