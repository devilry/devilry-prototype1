package org.devilry.clientapi;

import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;


public class AdminAssignment extends AbstractAssignment<AdminDelivery> {
	
	AdminAssignment(long assignmentId, DevilryConnection connection) {
		super(assignmentId, connection);
	}
	
	class AdminDeliveryIterator extends DeliveryIterator {
		
		AdminDeliveryIterator(List<Long> delivryIds) {
			super(delivryIds);
		}
		
		public AdminDelivery next() {
			return new AdminDelivery(deliveryIterator.next(), connection); 
		}
	}
		
	Iterator<AdminDelivery> deliveries() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
		return new AdminDeliveryIterator(ids).iterator();
	}
	
	public AdminDelivery addDelivery() throws NamingException, UnauthorizedException, NoSuchObjectException {
		long id = getDeliveryBean().create(assignmentId);
		return new AdminDelivery(id, connection);
	}
	
	public void removeDelivery(AdminDelivery delivery) throws NamingException, UnauthorizedException {
		getDeliveryBean().remove(delivery.deliveryId);
	}
}
