package org.devilry.clientapi;

import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.DeliveryCandidateCommon;
import org.devilry.core.daointerfaces.DeliveryCandidateLocal;
import org.devilry.core.daointerfaces.DeliveryCandidateRemote;

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
	
}
