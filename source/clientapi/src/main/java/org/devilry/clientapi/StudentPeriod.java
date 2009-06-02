package org.devilry.clientapi;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.daointerfaces.AssignmentNodeLocal;
import org.devilry.core.daointerfaces.DeliveryLocal;
import org.devilry.core.daointerfaces.PeriodNodeLocal;

public class StudentPeriod {

	DevilryConnection connection;
	
	PeriodNodeLocal periodNode;
	long periodId;
	
	StudentPeriod(long periodId, DevilryConnection connection) {
		this.connection = connection;
		this.periodId = periodId;
	}
	
	private PeriodNodeLocal getPeriodBean() throws NamingException {
		return periodNode == null ? periodNode = connection.getPeriodNode() : periodNode;
	}
	
	public Collection<StudentAssignment> getAssignments() throws NamingException {
		
		PeriodNodeLocal period = getPeriodBean();
	
		List<Long> ids = period.getAssignments(periodId);
		
		List<StudentAssignment> studentAssignments = new LinkedList<StudentAssignment>();
		StudentAssignment tmpAssignment;	
		
		for (long id : ids) {
			tmpAssignment = new StudentAssignment(id, connection);
			studentAssignments.add(tmpAssignment);
		}
		
		return studentAssignments;
	}
	
	public String getPath() throws NamingException {
		return getPeriodBean().getPath(periodId);
	}
	
}
