package org.devilry.clientapi.student;

import javax.naming.NamingException;

import org.devilry.clientapi.AbstractDeliveryCandidate;
import org.devilry.clientapi.DevilryConnection;
import org.devilry.clientapi.DevilryOutputStream;
import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.DeliveryCandidateLocal;

public class StudentDeliveryCandidate extends AbstractDeliveryCandidate {
	
	StudentDeliveryCandidate(long deliveryCandidateId, DevilryConnection connection) {
		super(deliveryCandidateId, connection);
	}
	
	
	public DevilryOutputStream addFile() {
		DevilryOutputStream outputStream = new DevilryOutputStream(deliveryCandidateId, connection);
		return outputStream;
	}
	
}
