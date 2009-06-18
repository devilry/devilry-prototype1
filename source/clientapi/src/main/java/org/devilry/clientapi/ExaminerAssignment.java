package org.devilry.clientapi;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;


public class ExaminerAssignment extends AbstractAssignment {
	
	ExaminerAssignment(long assignmentId, DevilryConnection connection) {
		super(assignmentId, connection);
	}
		
	public Collection<ExaminerDelivery> getDeliveries() throws NamingException, NoSuchObjectException, UnauthorizedException {
		
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
				
		List<ExaminerDelivery> deliveries = new LinkedList<ExaminerDelivery>();
		ExaminerDelivery deliveryTmp;
		
		for (long id : ids) {
			deliveryTmp = new ExaminerDelivery(id, connection);
			deliveries.add(deliveryTmp);
		}
		
		return deliveries;
	}
	
}
