package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;

public class StudentDelivery extends AbstractDelivery {

	DeliveryCommon delivery;
	long deliveryId;
	
	StudentDelivery(long deliveryId, DevilryConnection connection) {
		super(deliveryId, connection);
		this.deliveryId = deliveryId;
	}
	
	
	public StudentDeliveryCandidate createDeliveryCandidate() throws NamingException {
		
		DeliveryCandidateCommon deliveryCandidate = connection.getDeliveryCandidate();
		
		long candidateId = deliveryCandidate.create(deliveryId);
		StudentDeliveryCandidate candidate = new StudentDeliveryCandidate(candidateId, connection);
		
		return candidate;
	}
}
