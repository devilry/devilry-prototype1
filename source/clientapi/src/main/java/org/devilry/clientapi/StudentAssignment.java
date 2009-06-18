package org.devilry.clientapi;

import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;


public class StudentAssignment extends AbstractAssignment {
	
	StudentAssignment(long assignmentId, DevilryConnection connection) {
		super(assignmentId, connection);
	}
	
	
	public StudentDelivery getDelivery() throws NamingException, NoSuchObjectException, UnauthorizedException {
		List<Long> ids = getAssignmentNodeBean().getDeliveries(assignmentId);
		
		StudentDelivery delivery = new StudentDelivery(ids.get(0), connection);
		return delivery;
	}
	
	public NodePath getPath() throws NamingException, NoSuchObjectException {
		return getAssignmentNodeBean().getPath(assignmentId);
	}
}
