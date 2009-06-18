package org.devilry.clientapi;

import java.util.Date;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.AssignmentNodeRemote;
import org.devilry.core.daointerfaces.CourseNodeLocal;

public abstract class AbstractAssignment {

	protected DevilryConnection connection;
	
	AssignmentNodeCommon assignment;
	protected long assignmentId;
		
	protected AbstractAssignment(long assignmentId, DevilryConnection connection) {
		this.connection = connection;
		this.assignmentId = assignmentId;
	}
	
	
	protected AssignmentNodeCommon getAssignmentNodeBean() throws NamingException {
		return assignment == null ? assignment = connection.getAssignmentNode() : assignment;
	}
	
	public Date getDeadline() throws NoSuchObjectException, NamingException {
		return getAssignmentNodeBean().getDeadline(assignmentId);
	}
	
	public long getAssignmentId() {
		return assignmentId;
	}
	
}
