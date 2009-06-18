package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;


public class StudentAssignment extends AbstractAssignment<StudentDelivery> {
	
	StudentAssignment(long assignmentId, DevilryConnection connection) {
		super(assignmentId, connection);
	}
	
	class StudentDeliveryIterator extends DeliveryIterator {
		
		StudentDeliveryIterator(List<Long> delivryIds) {
			super(delivryIds);
		}
		
		public StudentDelivery next() {
			return new StudentDelivery(deliveryIterator.next(), connection); 
		}
	}
	
	
	Iterator<StudentDelivery> deliveries() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
		return new StudentDeliveryIterator(ids).iterator();
	}
}
