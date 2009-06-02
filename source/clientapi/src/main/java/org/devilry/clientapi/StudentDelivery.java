package org.devilry.clientapi;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.DeliveryCandidateLocal;
import org.devilry.core.daointerfaces.DeliveryLocal;

public class StudentDelivery extends AbstractDelivery {

	DeliveryLocal delivery;
	long deliveryId;
	
	StudentDelivery(long deliveryId, DevilryConnection connection) {
		super(deliveryId, connection);
		this.deliveryId = deliveryId;
	}
	
	
	
	public StudentDeliveryCandidate createDeliveryCandidate() throws NamingException {
		
		DeliveryCandidateLocal deliveryCandidate = connection.getDeliveryCandidate();
		
		long candidateId = deliveryCandidate.create(deliveryId);
		StudentDeliveryCandidate candidate = new StudentDeliveryCandidate(candidateId, connection);
		
		return candidate;
	}
}
