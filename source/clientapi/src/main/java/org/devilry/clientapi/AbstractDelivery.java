package org.devilry.clientapi;

import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.DeliveryCommon;
import org.devilry.core.daointerfaces.DeliveryLocal;
import org.devilry.core.daointerfaces.DeliveryRemote;

public abstract class AbstractDelivery {

	DeliveryCommon delivery;
	long deliveryId;
	
	int candidateCount = 0;
	
	protected DevilryConnection connection;
	
	protected AbstractDelivery(long deliveryId, DevilryConnection connection) {
		this.connection = connection;
		this.deliveryId = deliveryId;
	}
	
	private DeliveryCommon getDeliveryBean() throws NamingException {
		return delivery == null ? delivery = connection.getDelivery() : delivery;
	}
	
	
	
	
	public int status() {
		return 0;
	}
	
}
