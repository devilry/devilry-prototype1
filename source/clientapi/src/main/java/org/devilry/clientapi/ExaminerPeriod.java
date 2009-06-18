package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.clientapi.StudentPeriod.StudentAssignmentIterator;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.PeriodNodeCommon;


public class ExaminerPeriod {

	DevilryConnection connection;
	
	PeriodNodeCommon periodNode;
	
	long periodId;
	
	ExaminerPeriod(long periodId, DevilryConnection connection) {
		this.connection = connection;
		this.periodId = periodId;
	}
	
	
	private PeriodNodeCommon getPeriodBean() throws NamingException {
		return periodNode == null ? periodNode = connection.getPeriodNode() : periodNode;
	}
		
	
	class CorrectorAssignmentIterator implements Iterable<ExaminerAssignment>, Iterator<ExaminerAssignment> {

		Iterator<Long> assignmentIterator;
		
		CorrectorAssignmentIterator(List<Long> ids) {
			assignmentIterator = ids.iterator();
		}
		
		public Iterator<ExaminerAssignment> iterator() {
			return this;
		}

		public boolean hasNext() {
			return assignmentIterator.hasNext();
		}

		public ExaminerAssignment next() {
			return new ExaminerAssignment(assignmentIterator.next(), connection); 
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	Iterator<ExaminerAssignment> assignments() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> ids = getPeriodBean().getAssignments(periodId);
		return new CorrectorAssignmentIterator(ids).iterator();
	}
	
	
	public List<ExaminerAssignment> getAssignments() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<ExaminerAssignment> assignmentList = new LinkedList<ExaminerAssignment>();
		Iterator<ExaminerAssignment> iter = assignments();
		
		while (iter.hasNext()) {
			assignmentList.add(iter.next());
		}
		return assignmentList;
	}
	
	
	public NodePath getPath() throws NamingException, NoSuchObjectException {
		return getPeriodBean().getPath(periodId);
	}
}
