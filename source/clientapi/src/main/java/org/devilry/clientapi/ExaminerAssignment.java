package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;


public class ExaminerAssignment extends AbstractAssignment<ExaminerDelivery> {
	
	ExaminerAssignment(long assignmentId, DevilryConnection connection) {
		super(assignmentId, connection);
	}
		
	class ExaminerDeliveryIterator extends DeliveryIterator {
		
		ExaminerDeliveryIterator(List<Long> delivryIds) {
			super(delivryIds);
		}
		
		public ExaminerDelivery next() {
			return new ExaminerDelivery(deliveryIterator.next(), connection); 
		}
	}
		
	Iterator<ExaminerDelivery> deliveries() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
		return new ExaminerDeliveryIterator(ids).iterator();
	}
}
