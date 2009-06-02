package org.devilry.clientapi;

import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.DeliveryCandidateLocal;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;

public class AbstractDeliveryCandidate {

	int fileCount = 0;
	
	DeliveryCandidateLocal deliveryCandidate;
	long deliveryCandidateId;
	
	protected DevilryConnection connection;
	
	protected AbstractDeliveryCandidate(long deliveryCandidateId, DevilryConnection connection) {
		this.deliveryCandidateId = deliveryCandidateId;
		this.connection = connection;
	}
	
	protected DeliveryCandidateLocal getDeliveryBean() throws NamingException {
		return deliveryCandidate == null ? deliveryCandidate = connection.getDeliveryCandidate() : deliveryCandidate;
	}
	
	public List<AbstractDeliveryCandidate> getDeliveryFiles() {
		return null;
	}
		
	public int status() {
		return 0;
	}
	
	public Date getTimeOfDelivery() {
		return deliveryCandidate.getTimeOfDelivery(deliveryCandidateId);
	}
	
	public int getFileCount() {
		return fileCount;
	}
	
}
