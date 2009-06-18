package org.devilry.clientapi;

import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.clientapi.AbstractDelivery.DeliveryCandidateIterator;
import org.devilry.clientapi.StudentDelivery.StudentDeliveryCandidateIterator;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCommon;


public class ExaminerDelivery extends AbstractDelivery<ExaminerDeliveryCandidate> {
		
	ExaminerDelivery(long deliveryId, DevilryConnection connection) {
		super(deliveryId, connection);
	}
		
	
	class ExaminerDeliveryCandidateIterator extends DeliveryCandidateIterator {

		ExaminerDeliveryCandidateIterator(List<Long> ids) {
			super(ids);
		}
		
		public ExaminerDeliveryCandidate next() {
			return new ExaminerDeliveryCandidate(deliveryCandidateIterator.next(), connection); 
		}
	}
		
	Iterator<ExaminerDeliveryCandidate> candidates() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> candidates = getDeliveryBean().getDeliveryCandidates(deliveryId);
		return new ExaminerDeliveryCandidateIterator(candidates).iterator();
	}
		
	public StudentDeliveryCandidate getDeliveryCandidate() {
		return null;
	}
	
}
