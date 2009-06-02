package org.devilry.clientapi;

import java.util.Date;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.CourseNodeLocal;

public abstract class AbstractAssignment {

	protected DevilryConnection connection;
	
	AssignmentNodeLocal assignment;
	protected long assignmentId;
	
	Date deadline;
	
	protected AbstractAssignment(long assignmentId, DevilryConnection connection) {
		this.connection = connection;
		this.assignmentId = assignmentId;
	}
	
	
	protected AssignmentNodeLocal getAssignmentNodeBean() throws NamingException {
		return assignment == null ? assignment = connection.getAssignmentNode() : assignment;
	}
	
	public Date getDeadline() {
		return deadline;
	}
	
	
	
}
