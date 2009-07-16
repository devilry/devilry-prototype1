package org.devilry.clientapi;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.InvalidNameException;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.PathExistsException;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.AssignmentNodeCommon;
import org.devilry.core.daointerfaces.PeriodNodeCommon;


public class AdminPeriod extends AbstractPeriod<AdminAssignment> {

	AdminPeriod(long periodId, DevilryConnection connection) {
		super(periodId, connection);
	}
		
	AssignmentNodeCommon assignmentNode;
	
	protected AssignmentNodeCommon getAssignmentBean() throws NamingException {
		return assignmentNode == null ? assignmentNode = connection.getAssignmentNode() : assignmentNode;
	}
	
	class StudentAssignmentIterator extends AssignmentIterator {

		StudentAssignmentIterator(List<Long> IDs) {
			super(IDs);
		}
		
		public AdminAssignment next() {
			return new AdminAssignment(assignmentIterator.next(), connection); 
		}
	}
	
	public Iterator<AdminAssignment> assignments() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> ids = getPeriodBean().getAssignments(periodId);
		return new StudentAssignmentIterator(ids).iterator();
	}
	

	public List<AdminPeriod> getPeriodsWhereIsAdmin() throws UnauthorizedException, NamingException {
		
		List<Long> nodes = getPeriodBean().getPeriodsWhereIsAdmin();
	
		List<AdminPeriod> adminPeriods = new LinkedList<AdminPeriod>();
		
		for (long id : nodes) {
			adminPeriods.add(new AdminPeriod(id, connection));
		}
		return adminPeriods;
	}
	
	public List<AdminAssignment> getAssignments() throws UnauthorizedException, NoSuchObjectException, NamingException {
		
		List<Long> assignments = getPeriodBean().getAssignments(periodId);
		List<AdminAssignment> periodList = new LinkedList<AdminAssignment>();
		
		for (long id : assignments) {
			periodList.add(new AdminAssignment(id, connection));
		}
		return periodList;
	}
	
	
	public AdminAssignment addAssignment(String name, String displayName, Date deadline) throws PathExistsException, UnauthorizedException, InvalidNameException, NoSuchObjectException, NamingException {
		long assignmentId = getAssignmentBean().create(name, displayName, deadline, periodId);
		return new AdminAssignment(assignmentId, connection);
	}

	public void removePeriod(AdminAssignment assignment) throws NoSuchObjectException, NamingException {
		getAssignmentBean().remove(assignment.assignmentId);
	}

	public void setAssignmentName(AdminAssignment assignment, String newName) throws UnauthorizedException, NoSuchObjectException, NamingException {
		
		List<Long> assignments = getPeriodBean().getAssignments(periodId);
		
		if (assignments.contains(assignment.assignmentId)) {
			getAssignmentBean().setName(assignment.assignmentId, newName);
		}
		else {
			System.err.println("Does not contain assignment with id:" + assignment.assignmentId);
		}
	}

	void setAssignmentDisplayName(AdminAssignment assignment, String newName) throws UnauthorizedException, NoSuchObjectException, NamingException {
		
		List<Long> assignments = getPeriodBean().getAssignments(periodId);
		
		if (assignments.contains(assignment.assignmentId)) {
			getAssignmentBean().setDisplayName(assignment.assignmentId, newName);
		}
		else {
			System.err.println("Does not contain assignment with id:" + assignment.assignmentId);
		}
	}
	
	void setAssignmentDeadline(AdminAssignment assignment, Date newDeadline) throws UnauthorizedException, NoSuchObjectException, NamingException {
		
		List<Long> assignments = getPeriodBean().getAssignments(periodId);
		
		if (assignments.contains(assignment.assignmentId)) {
			getAssignmentBean().setDeadline(assignment.assignmentId, newDeadline);
		}
		else {
			System.err.println("Does not contain assignment with id:" + assignment.assignmentId);
		}
	}
	
	public void setPeriodName(String newName) throws UnauthorizedException, NoSuchObjectException, NamingException {
		getPeriodBean().setName(periodId, newName);
	}

	public void setPeriodDisplayName(String newName) throws UnauthorizedException, NoSuchObjectException, NamingException {
		getPeriodBean().setDisplayName(periodId, newName);
	}
	
	public void setPeriodStartDate(Date newDate) throws UnauthorizedException, NoSuchObjectException, NamingException {
		getPeriodBean().setStartDate(periodId, newDate);
	}
	
	public void setPeriodEndDate(Date newDate) throws UnauthorizedException, NoSuchObjectException, NamingException {
		getPeriodBean().setEndDate(periodId, newDate);
	}
}
