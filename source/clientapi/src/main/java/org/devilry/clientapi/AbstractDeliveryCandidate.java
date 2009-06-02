package org.devilry.clientapi;

import java.util.Date;
import java.util.List;

import org.devilry.core.daointerfaces.DeliveryCandidateRemote;

public class AbstractDeliveryCandidate {

	Date timeOfDelivery;
	int fileCount = 0;
	
	DeliveryCandidateRemote deliveryCandidate;
	
	protected DevilryConnection connection;
	
	protected AbstractDeliveryCandidate(DevilryConnection connection) {
		this.connection = connection;
	}
	
	public List<AbstractDeliveryCandidate> getDeliveryFiles() {
		return null;
	}
		
	public int status() {
		return 0;
	}
	
	public Date getTimeOfDelivery() {
		return timeOfDelivery;
	}
	
	public int getFileCount() {
		return fileCount;
	}
	
}
