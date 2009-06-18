package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;

public class StudentDelivery extends AbstractDelivery<StudentDeliveryCandidate> {
	
	StudentDelivery(long deliveryId, DevilryConnection connection) {
		super(deliveryId, connection);
	}
		
	public StudentDeliveryCandidate createDeliveryCandidate() throws NamingException {
		
		DeliveryCandidateCommon deliveryCandidate = connection.getDeliveryCandidate();
		
		long candidateId = deliveryCandidate.create(deliveryId);
		StudentDeliveryCandidate candidate = new StudentDeliveryCandidate(candidateId, connection);
		
		return candidate;
	}
	
	
	class StudentDeliveryCandidateIterator extends DeliveryCandidateIterator {

		StudentDeliveryCandidateIterator(List<Long> ids) {
			super(ids);
		}
		
		public StudentDeliveryCandidate next() {
			return new StudentDeliveryCandidate(deliveryCandidateIterator.next(), connection); 
		}
	}
		
	Iterator<StudentDeliveryCandidate> candidates() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> candidates = getDeliveryBean().getDeliveryCandidates(deliveryId);
		return new StudentDeliveryCandidateIterator(candidates).iterator();
	}
		
	public StudentDeliveryCandidate getDeliveryCandidate() {
		return null;
	}
}
