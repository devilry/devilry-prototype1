package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.DeliveryCommon;

public abstract class AbstractDelivery<E extends AbstractDeliveryCandidate> {

	DeliveryCommon delivery;
	long deliveryId;
	
	int candidateCount = 0;
	
	protected DevilryConnection connection;
	
	protected AbstractDelivery(long deliveryId, DevilryConnection connection) {
		this.connection = connection;
		this.deliveryId = deliveryId;
	}
	
	protected DeliveryCommon getDeliveryBean() throws NamingException {
		return delivery == null ? delivery = connection.getDelivery() : delivery;
	}
	
	
	public int getStatus() throws NamingException {
		return getDeliveryBean().getStatus(deliveryId);
	}
	
	abstract class DeliveryCandidateIterator implements Iterable<E>, Iterator<E> {

		Iterator<Long> deliveryCandidateIterator;
		
		DeliveryCandidateIterator(List<Long> ids) {
			deliveryCandidateIterator = ids.iterator();
		}
		
		public Iterator<E> iterator() {
			return this;
		}

		public boolean hasNext() {
			return deliveryCandidateIterator.hasNext();
		}

		public abstract E next();

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	abstract Iterator<E> deliveryCandidates() throws NoSuchObjectException, UnauthorizedException, NamingException;
	
	public List<E> getDeliveryCandidates() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<E> candidateList = new LinkedList<E>();
		Iterator<E> iter = deliveryCandidates();
		
		while (iter.hasNext()) {
			candidateList.add(iter.next());
		}
		return candidateList;
	}
	
}
