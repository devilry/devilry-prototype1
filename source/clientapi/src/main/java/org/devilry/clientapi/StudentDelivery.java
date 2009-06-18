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
	
	
	class StudentDeliveryCandidateIterator implements Iterable<StudentDeliveryCandidate>, Iterator<StudentDeliveryCandidate> {

		Iterator<Long> deliveryCandidateIterator;
		
		StudentDeliveryCandidateIterator(List<Long> ids) {
			deliveryCandidateIterator = ids.iterator();
		}
		
		public Iterator<StudentDeliveryCandidate> iterator() {
			return this;
		}

		public boolean hasNext() {
			return deliveryCandidateIterator.hasNext();
		}

		public StudentDeliveryCandidate next() {
			return new StudentDeliveryCandidate(deliveryCandidateIterator.next(), connection); 
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	
	Iterator<StudentDeliveryCandidate> candidates() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> candidates = getDeliveryBean().getDeliveryCandidates(deliveryId);
		return new StudentDeliveryCandidateIterator(candidates).iterator();
	}
	
	
	public List<StudentDeliveryCandidate> getDeliveryCandidates() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<StudentDeliveryCandidate> candidateList = new LinkedList<StudentDeliveryCandidate>();
		Iterator<StudentDeliveryCandidate> iter = candidates();
		
		while (iter.hasNext()) {
			candidateList.add(iter.next());
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
