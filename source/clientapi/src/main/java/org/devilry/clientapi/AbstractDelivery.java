package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.clientapi.StudentDelivery.StudentDeliveryCandidateIterator;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.DeliveryCommon;
import org.devilry.core.daointerfaces.DeliveryLocal;
import org.devilry.core.daointerfaces.DeliveryRemote;

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
	
	abstract Iterator<E> candidates() throws NoSuchObjectException, UnauthorizedException, NamingException;
	
	public List<E> getDeliveryCandidates() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<E> candidateList = new LinkedList<E>();
		Iterator<E> iter = candidates();
		
		while (iter.hasNext()) {
			candidateList.add(iter.next());
		}
		return candidateList;
	}
	
}
