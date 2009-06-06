package org.devilry.clientapi;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NodePath;
import org.devilry.core.daointerfaces.PeriodNodeCommon;


public class StudentPeriod {

	DevilryConnection connection;
	
	PeriodNodeCommon periodNode;
	long periodId;
	
	StudentPeriod(long periodId, DevilryConnection connection) {
		this.connection = connection;
		this.periodId = periodId;
	}
	
	private PeriodNodeCommon getPeriodBean() throws NamingException {
		return periodNode == null ? periodNode = connection.getPeriodNode() : periodNode;
	}
	
	public Collection<StudentAssignment> getAssignments() throws NamingException {
		
		PeriodNodeCommon period = getPeriodBean();
	
		List<Long> ids = period.getAssignments(periodId);
		
		List<StudentAssignment> studentAssignments = new LinkedList<StudentAssignment>();
		StudentAssignment tmpAssignment;	
		
		for (long id : ids) {
			tmpAssignment = new StudentAssignment(id, connection);
			studentAssignments.add(tmpAssignment);
		}
		
		return studentAssignments;
	}
	
	public NodePath getPath() throws NamingException {
		return getPeriodBean().getPath(periodId);
	}
	
}
