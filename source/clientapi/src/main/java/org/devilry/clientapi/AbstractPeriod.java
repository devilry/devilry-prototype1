package org.devilry.clientapi;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.naming.NamingException;

import org.devilry.core.NoSuchObjectException;
import org.devilry.core.NodePath;
import org.devilry.core.UnauthorizedException;
import org.devilry.core.daointerfaces.PeriodNodeCommon;


public abstract class AbstractPeriod<E extends AbstractAssignment<?>> {

	DevilryConnection connection;
	
	PeriodNodeCommon periodNode;
	long periodId;
	
	AbstractPeriod(long periodId, DevilryConnection connection) {
		this.connection = connection;
		this.periodId = periodId;
	}
	
	protected PeriodNodeCommon getPeriodBean() throws NamingException {
		return periodNode == null ? periodNode = connection.getPeriodNode() : periodNode;
	}
		
	
	abstract class AssignmentIterator implements Iterable<E>, Iterator<E> {

		Iterator<Long> assignmentIterator;
		
		AssignmentIterator(List<Long> ids) {
			assignmentIterator = ids.iterator();
		}
		
		public Iterator<E> iterator() {
			return this;
		}

		public boolean hasNext() {
			return assignmentIterator.hasNext();
		}
		
		abstract public E next();

		/*public StudentAssignment next() {
			return new StudentAssignment(assignmentIterator.next(), connection); 
		}*/

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	public abstract Iterator<E> assignments() throws NoSuchObjectException, UnauthorizedException, NamingException;
		
	
	public List<E> getAssignments() throws NamingException, NoSuchObjectException, UnauthorizedException {
				
		LinkedList<E> assignmentList = new LinkedList<E>();
		Iterator<E> iter = assignments();
		
		while (iter.hasNext()) {
			assignmentList.add(iter.next());
		}
		return assignmentList;
	}
	
	
	public NodePath getPath() throws NamingException, NoSuchObjectException {
		return getPeriodBean().getPath(periodId);
	}

	public String getPeriodName() throws NoSuchObjectException, NamingException {
		return getPeriodBean().getName(periodId);
	}
	
	public String getPeriodDisplayName() throws NoSuchObjectException, NamingException {
		return getPeriodBean().getDisplayName(periodId);
	}	
	
	public Date getPeriodStartDate() throws UnauthorizedException, NoSuchObjectException, NamingException {
		return getPeriodBean().getStartDate(periodId);
	}
	
	public Date getPeriodEndDate() throws UnauthorizedException, NoSuchObjectException, NamingException {
		return getPeriodBean().getEndDate(periodId);
	}
}


