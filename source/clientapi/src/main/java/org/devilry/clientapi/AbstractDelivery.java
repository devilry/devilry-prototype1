package org.devilry.clientapi;

import java.util.List;

import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.DeliveryRemote;

public abstract class AbstractDelivery {

	DeliveryRemote delivery;
	long deliveryId;
	
	int candidateCount = 0;
	
	protected DevilryConnection connection;
	
	protected AbstractDelivery(long deliveryId, DevilryConnection connection) {
		this.connection = connection;
		this.deliveryId = deliveryId;
	}
	
	public List<AbstractDeliveryCandidate> getDeliveryCandidates() {
		
		List<Long> deliveries = delivery.getDeliveryCandidates(deliveryId);
		
		
		
		return null;
	}
	
	public AbstractDeliveryCandidate getDeliveryCandidate() {
		return null;
	}
	
	public int status() {
		return 0;
	}
	
}
