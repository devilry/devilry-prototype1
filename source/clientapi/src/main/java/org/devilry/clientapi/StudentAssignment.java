package org.devilry.clientapi;

import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;


public class StudentAssignment extends AbstractAssignment {
	
	StudentAssignment(long assignmentId, DevilryConnection connection) {
		super(assignmentId, connection);
	}
	
	
	public List<StudentDelivery> getDeliveries() throws NamingException, NoSuchObjectException, UnauthorizedException {
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
		
		LinkedList<StudentDelivery> deliveries = new LinkedList<StudentDelivery>();
		
		for (long id : ids) {
			StudentDelivery delivery = new StudentDelivery(id, connection);
			deliveries.add(delivery);
		}
		return deliveries;
	}
	
	public NodePath getPath() throws NamingException, NoSuchObjectException {
		return getAssignmentNodeBean().getPath(assignmentId);
	}
}
