package org.devilry.clientapi;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.DeliveryCandidateCommon;

public class AbstractDeliveryCandidate {
	
	DeliveryCandidateCommon deliveryCandidate;
	protected long deliveryCandidateId;
	
	protected DevilryConnection connection;
	
	protected AbstractDeliveryCandidate(long deliveryCandidateId, DevilryConnection connection) {
		this.deliveryCandidateId = deliveryCandidateId;
		this.connection = connection;
	}
	
	protected DeliveryCandidateCommon getDeliveryCandidateBean() throws NamingException {
		return deliveryCandidate == null ? deliveryCandidate = connection.getDeliveryCandidate() : deliveryCandidate;
	}
	
		
	public int getStatus() throws NamingException {
		return getDeliveryCandidateBean().getStatus(deliveryCandidateId);
	}
	
	public Date getTimeOfDelivery() {
		return deliveryCandidate.getTimeOfDelivery(deliveryCandidateId);
	}
	
	public int getFileCount() throws NamingException {
		return getDeliveryCandidateBean().getFiles(deliveryCandidateId).size();
	}
	
	class DevilryInputStreamIterator implements Iterable<DevilryInputStream>, Iterator<DevilryInputStream> {

		Iterator<Long> fileMetaIdIterator;
		
		DevilryInputStreamIterator(List<Long> ids) {
			fileMetaIdIterator = ids.iterator();
		}
		
		public Iterator<DevilryInputStream> iterator() {
			return this;
		}

		public boolean hasNext() {
			return fileMetaIdIterator.hasNext();
		}

		public DevilryInputStream next() {
			return new DevilryInputStream(fileMetaIdIterator.next(), connection); 
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
		
	Iterator<DevilryInputStream> deliveries() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> fileIds = getDeliveryCandidateBean().getFiles(deliveryCandidateId);
		return new DevilryInputStreamIterator(fileIds).iterator();
	}
	
	
	public List<DevilryInputStream> getDeliveryFiles() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<DevilryInputStream> candidateList = new LinkedList<DevilryInputStream>();
		Iterator<DevilryInputStream> iter = deliveries();
		
		while (iter.hasNext()) {
			candidateList.add(iter.next());
		}
		return candidateList;
	}
}
