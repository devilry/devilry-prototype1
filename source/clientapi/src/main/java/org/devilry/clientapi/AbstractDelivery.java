package org.devilry.clientapi;

import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.DeliveryLocal;
import org.devilry.core.daointerfaces.DeliveryRemote;

public abstract class AbstractDelivery {

	DeliveryLocal delivery;
	long deliveryId;
	
	int candidateCount = 0;
	
	protected DevilryConnection connection;
	
	protected AbstractDelivery(long deliveryId, DevilryConnection connection) {
		this.connection = connection;
		this.deliveryId = deliveryId;
	}
	
	private DeliveryLocal getDeliveryBean() throws NamingException {
		return delivery == null ? delivery = connection.getDelivery() : delivery;
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
