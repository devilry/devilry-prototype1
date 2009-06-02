package org.devilry.clientapi.student;

import javax.naming.NamingException;

import org.devilry.clientapi.AbstractDelivery;
import org.devilry.clientapi.DevilryConnection;
import org.devilry.core.daointerfaces.CourseNodeLocal;
import org.devilry.core.daointerfaces.DeliveryLocal;

public class StudentDelivery extends AbstractDelivery {

	DeliveryLocal delivery;
	long deliveryId;
	
	StudentDelivery(long deliveryId, DevilryConnection connection) {
		super(connection);
		this.deliveryId = deliveryId;
	}
	
	private DeliveryLocal getDeliveryBean() throws NamingException {
		
		if (delivery == null)
			delivery = connection.getDelivery();
		
		return delivery;
	}
	
	public StudentDeliveryCandidate addDeliveryCandidate() {
		return null;
	}
}
