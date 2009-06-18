package org.devilry.clientapi;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.clientapi.AbstractPeriod.AssignmentIterator;
import org.devilry.clientapi.StudentPeriod.StudentAssignmentIterator;
import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.PeriodNodeCommon;


public class ExaminerPeriod extends AbstractPeriod<ExaminerAssignment> {

	ExaminerPeriod(long periodId, DevilryConnection connection) {
		super(periodId, connection);
	}
	
	class ExaminerAssignmentIterator extends AssignmentIterator {

		ExaminerAssignmentIterator(List<Long> IDs) {
			super(IDs);
		}
		
		public ExaminerAssignment next() {
			return new ExaminerAssignment(assignmentIterator.next(), connection); 
		}
	}
	
	public Iterator<ExaminerAssignment> assignments() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> ids = getPeriodBean().getAssignments(periodId);
		return new ExaminerAssignmentIterator(ids).iterator();
	}
}
