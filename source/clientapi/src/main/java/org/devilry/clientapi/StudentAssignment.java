package org.devilry.clientapi;

import java.util.List;

import javax.naming.NamingException;


public class StudentAssignment extends AbstractAssignment {
	
	StudentAssignment(long assignmentId, DevilryConnection connection) {
		super(assignmentId, connection);
	}
	
	
	public StudentDelivery getDelivery() throws NamingException {
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
		
		StudentDelivery delivery = new StudentDelivery(ids.get(0), connection);
		return delivery;
	}
	
	public String getPath() throws NamingException {
		return getAssignmentNodeBean().getPath(assignmentId);
	}
}
