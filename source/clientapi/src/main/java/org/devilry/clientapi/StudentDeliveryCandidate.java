package org.devilry.clientapi;


public class StudentDeliveryCandidate extends AbstractDeliveryCandidate {
	
	StudentDeliveryCandidate(long deliveryCandidateId, DevilryConnection connection) {
		super(deliveryCandidateId, connection);
	}
	
	
	public DevilryOutputStream addFile() {
		DevilryOutputStream outputStream = new DevilryOutputStream(deliveryCandidateId, connection);
		return outputStream;
	}
	
}
