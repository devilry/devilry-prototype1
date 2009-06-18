package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;
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
	
	
	
	class StudentAssignmentIterator implements Iterable<StudentAssignment>, Iterator<StudentAssignment> {

		Iterator<Long> assignmentIterator;
		
		StudentAssignmentIterator(List<Long> ids) {
			assignmentIterator = ids.iterator();
		}
		
		public Iterator<StudentAssignment> iterator() {
			return this;
		}

		public boolean hasNext() {
			return assignmentIterator.hasNext();
		}

		public StudentAssignment next() {
			return new StudentAssignment(assignmentIterator.next(), connection); 
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	Iterator<StudentAssignment> assignments() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> ids = getPeriodBean().getAssignments(periodId);
		return new StudentAssignmentIterator(ids).iterator();
	}
	
	
	public List<StudentAssignment> getAssignments() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<StudentAssignment> assignmentList = new LinkedList<StudentAssignment>();
		Iterator<StudentAssignment> iter = assignments();
		
		while (iter.hasNext()) {
			assignmentList.add(iter.next());
		}
		return assignmentList;
	}
	
	
	public NodePath getPath() throws NamingException, NoSuchObjectException {
		return getPeriodBean().getPath(periodId);
	}
	
}
