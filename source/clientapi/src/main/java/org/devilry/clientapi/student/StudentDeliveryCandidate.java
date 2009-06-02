package org.devilry.clientapi.student;

import javax.naming.NamingException;

import org.devilry.clientapi.AbstractDeliveryCandidate;
import org.devilry.clientapi.DevilryConnection;
import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.DeliveryCandidateLocal;

public class StudentDeliveryCandidate extends AbstractDeliveryCandidate {

	DeliveryCandidateLocal deliveryCandidate;
	long deliveryCandidateId;
	
	StudentDeliveryCandidate(long deliveryCandidateId, DevilryConnection connection) {
		super(connection);
		this.deliveryCandidateId = deliveryCandidateId;
	}
		
	private DeliveryCandidateLocal getDeliveryBean() throws NamingException {
		
		if (deliveryCandidate == null)
			deliveryCandidate = connection.getDeliveryCandidate();
		
		return deliveryCandidate;
	}
	
	/*
	public addFile() {
		
	}
	*/
}
