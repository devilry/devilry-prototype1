package org.devilry.clientapi;

import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.UnauthorizedException;


public class StudentPeriod extends AbstractPeriod<StudentAssignment> {

	StudentPeriod(long periodId, DevilryConnection connection) {
		super(periodId, connection);
	}
		
	class StudentAssignmentIterator extends AssignmentIterator {

		StudentAssignmentIterator(List<Long> IDs) {
			super(IDs);
		}
		
		public StudentAssignment next() {
			return new StudentAssignment(assignmentIterator.next(), connection); 
		}
	}
	
	public Iterator<StudentAssignment> assignments() throws NoSuchObjectException, UnauthorizedException, NamingException {
		List<Long> ids = getPeriodBean().getAssignments(periodId);
		return new StudentAssignmentIterator(ids).iterator();
	}
}
